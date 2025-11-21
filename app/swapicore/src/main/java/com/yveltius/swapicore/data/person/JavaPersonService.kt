package com.yveltius.swapicore.data.person

import com.yveltius.swapicore.data.Service
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.entity.http.JavaHttpClient
import com.yveltius.swapicore.fromJsonString

private const val PEOPLE_URI_STRING = "https://swapi.info/api/people"

internal class JavaPersonService : Service(httpClient = JavaHttpClient()), PersonService {
    override suspend fun getPeople(): Result<List<Person>> {
        return makeRequest(
            onFailureTag = "JavaPersonService",
            onFailureMessage = "Failed to get people.",
        ) {
            val result = httpClient
                .getJson(urlString = PEOPLE_URI_STRING)
                .getOrThrow()
                .fromJsonString<List<Person>>()

            result
        }
    }
}
