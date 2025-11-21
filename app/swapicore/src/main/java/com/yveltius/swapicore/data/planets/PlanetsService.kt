package com.yveltius.swapicore.data.planets

import com.yveltius.swapicore.entity.api.Planet

internal interface PlanetsService {
    suspend fun getPlanets(): Result<List<Planet>>

    companion object {
        fun getInstance(): PlanetsService {
            return JavaPlanetsService()
        }
    }
}