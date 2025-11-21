package com.yveltius.swapicore.domain.planets

import com.yveltius.swapicore.data.planets.PlanetsService
import com.yveltius.swapicore.entity.api.Planet

class PlanetsUseCase internal constructor(
    private val planetsService: PlanetsService
) {
    suspend fun getPlanets(): Result<List<Planet>> = planetsService.getPlanets()

    companion object {
        fun getInstance(): PlanetsUseCase {
            return PlanetsUseCase(
                planetsService = PlanetsService.getInstance()
            )
        }
    }
}