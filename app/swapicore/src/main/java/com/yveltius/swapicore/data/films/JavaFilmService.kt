package com.yveltius.swapicore.data.films

import com.yveltius.swapicore.data.Service
import com.yveltius.swapicore.entity.api.Film
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.entity.http.JavaHttpClient
import com.yveltius.swapicore.ext.fromJsonString
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

private const val FILMS_URI_STRING = "https://swapi.info/api/films"

internal class JavaFilmService : FilmService, Service(httpClient = JavaHttpClient()) {
    override suspend fun getFilms(): Result<List<Film>> {
        return makeRequest(
            onFailureTag = "JavaFilmService",
            onFailureMessage = "Failed to get all films."
        ) {
            val result = httpClient
                .getJson(urlString = FILMS_URI_STRING)
                .getOrThrow()
                .fromJsonString<List<Film>>()

            result
        }
    }

    override suspend fun getFilmsForPerson(person: Person): Result<List<Film>> {
        return makeRequest(
            onFailureTag = "JavaFilmService",
            onFailureMessage = "Failed to get films for Person($person)."
        ) { coroutineScope ->
            val result = person.filmUrls
                .map { filmUrl ->
                    coroutineScope.async {
                        httpClient
                            .getJson(urlString = filmUrl)
                            .getOrNull()
                            ?.fromJsonString<Film>()
                            ?.getOrThrow()
                    }
                }.awaitAll()

            if (result.any { it == null }) {
                return@makeRequest Result.failure(Throwable("Failed to get all films for ${person.name}."))
            }

            // linter really wants me to ensure no nulls are present
            Result.success(result.filterNotNull())
        }
    }
}