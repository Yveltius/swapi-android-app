package com.yveltius.swapicore.domain.films

import com.yveltius.swapicore.data.films.FilmService
import com.yveltius.swapicore.data.films.JavaFilmService
import com.yveltius.swapicore.entity.api.Film
import com.yveltius.swapicore.entity.api.Person

class FilmsUseCase internal constructor(
    private val filmService: FilmService
) {
    suspend fun getFilms(): Result<List<Film>> = filmService.getFilms()

    suspend fun getFilmsForPerson(person: Person): Result<List<Film>> {
        return filmService.getFilmsForPerson(person)
    }

    companion object {
        fun getInstance(): FilmsUseCase {
            return FilmsUseCase(
                filmService = JavaFilmService()
            )
        }
    }
}