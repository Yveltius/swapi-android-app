package com.yveltius.swapi.planets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yveltius.swapicore.domain.planets.PlanetsUseCase
import com.yveltius.swapicore.entity.api.Planet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlanetsViewModel : ViewModel() {
    private val planetsUseCase: PlanetsUseCase = PlanetsUseCase.getInstance()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getPlanets()
    }

    fun getPlanets() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            planetsUseCase.getPlanets()
                .onSuccess { planets ->
                    _uiState.update {
                        it.copy(isLoading = false, hasError = false, planets = planets)
                    }
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(isLoading = false, hasError = true, planets = listOf())
                    }
                }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val planets: List<Planet> = listOf()
    )
}