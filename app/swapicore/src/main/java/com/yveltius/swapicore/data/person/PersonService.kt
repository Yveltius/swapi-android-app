package com.yveltius.swapicore.data.person

import com.yveltius.swapicore.entity.api.Person

internal interface PersonService {
    suspend fun getPeople(): Result<List<Person>>

    companion object {
        const val PEOPLE_URI_STRING = "https://swapi.info/api/people"

		fun getInstance(): PersonService {
			return JavaPersonService()
		}
    }
}
