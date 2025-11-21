package com.yveltius.swapicore.data.films

import com.yveltius.swapicore.entity.api.Film
import com.yveltius.swapicore.entity.api.Person

internal interface FilmService {
    suspend fun getFilms(): Result<List<Film>>
    suspend fun getFilmsForPerson(person: Person): Result<List<Film>>

    companion object {

        fun getInstance(): FilmService {
            return JavaFilmService()
        }
    }
}