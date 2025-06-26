package com.yveltius.swapicore.data.person

import com.yveltius.swapicore.data.Service
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.entity.http.JavaHttpClient
import com.yveltius.swapicore.fromJsonString

internal class JavaPersonService : Service(httpClient = JavaHttpClient()), PersonService {
    override suspend fun getPeople(): Result<List<Person>> {
        return makeRequest(
            onFailureTag = "PersonServiceImpl",
            onFailureMessage = "Failed to get people.",
            block = {
                val result = httpClient
                    .getJson(urlString = PersonService.PEOPLE_URI_STRING)
                    .getOrThrow()
                    .fromJsonString<List<Person>>()

                result
            }
        )
    }
}
