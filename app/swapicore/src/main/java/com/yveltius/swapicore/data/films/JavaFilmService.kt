package com.yveltius.swapicore.data.films

import com.yveltius.swapicore.data.Service
import com.yveltius.swapicore.entity.api.Film
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.entity.http.JavaHttpClient
import com.yveltius.swapicore.fromJsonString
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

internal class JavaFilmService : FilmService, Service(httpClient = JavaHttpClient()) {
    override suspend fun getFilms(): Result<List<Film>> {
        return makeRequest(
            onFailureTag = "JavaFilmService",
            onFailureMessage = "Failed to get all films."
        ) {
            val result = httpClient
                .getJson(urlString = FilmService.FILMS_URI_STRING)
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
                            .getOrThrow()
                            .fromJsonString<Film>()
                            .getOrThrow()
                    }
                }.awaitAll()

            // alternative - list with nullable type and have nulls for the failures?

            // this requires all film calls to be successful in List
            Result.success(result)
        }
    }
}