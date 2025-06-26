package com.yveltius.swapicore.domain.person

import com.yveltius.swapicore.data.person.PersonService
import com.yveltius.swapicore.entity.api.Person

class PersonUseCase internal constructor(
    private val personService: PersonService
) {
    suspend fun getPeople(): Result<List<Person>> {
        return personService.getPeople()
    }

    companion object {
        fun getInstance(): PersonUseCase {
            return PersonUseCase(
                personService = PersonService.getInstance()
            )
        }
    }
}