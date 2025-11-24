package com.yveltius.swapi.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yveltius.swapicore.domain.films.FilmsUseCase
import com.yveltius.swapicore.entity.api.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel: ViewModel() {
    private val filmsUseCase: FilmsUseCase = FilmsUseCase.getInstance()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getFilms()
    }

    private fun getFilms() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            filmsUseCase.getFilms()
                .onSuccess { films ->
                    _uiState.update {
                        it.copy(
                            films = films,
                            isLoading = false
                        )
                    }
                }.onFailure { throwable ->
                    println(throwable)
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
        }
    }

    data class UiState(
        val films: List<Film> = listOf(),
        val isLoading: Boolean = false,
        val hasError: Boolean = false
    )
}