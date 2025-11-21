package com.yveltius.swapicore.data.planets

import com.yveltius.swapicore.data.Service
import com.yveltius.swapicore.entity.api.Planet
import com.yveltius.swapicore.entity.http.JavaHttpClient
import com.yveltius.swapicore.fromJsonString

private const val PLANETS_URI_STRING = "https://swapi.info/api/planets"
internal class JavaPlanetsService : Service(httpClient = JavaHttpClient()), PlanetsService {
    override suspend fun getPlanets(): Result<List<Planet>> {
        return makeRequest(
            onFailureTag = "JavaPlanetService",
            onFailureMessage = "Failed to get planets."
        ) {
            val result = httpClient
                .getJson(urlString = PLANETS_URI_STRING)
                .getOrThrow()
                .fromJsonString<List<Planet>>()

            result
        }
    }
}