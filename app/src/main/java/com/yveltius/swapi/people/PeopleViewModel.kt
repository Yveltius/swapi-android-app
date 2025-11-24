package com.yveltius.swapi.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yveltius.swapicore.domain.person.PersonUseCase
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.ext.finally
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {
    private val personUseCase: PersonUseCase = PersonUseCase.getInstance()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getPeople()
    }

    private fun getPeople() {
        viewModelScope.launch {
            personUseCase.getPeople()
                .onSuccess { people ->
                    _uiState.update {
                        it.copy(
                            people = people,
                            hairColorSelection = createHairColorSelection(people)
                        )
                    }
                }.onFailure {
                    println("Failed to get people.\n${it.message}")
                }.finally {
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun createHairColorSelection(people: List<Person>): FilterOptionSelection {
        val hairColors = people
            .map { person -> person.hairColor }
            .filter { hairColor ->
                // remove any multi-color options
                hairColor.contains(',').not()
            }
            .map { hairColor ->
                when {
                    hairColor.contains('/') -> hairColor.uppercase()
                    else -> hairColor.first().uppercaseChar() + hairColor.substring(startIndex = 1)
                }
            }
            .distinct()

        return FilterOptionSelection(options = hairColors)
    }

    fun onSortOptionChanged(newSortOption: SortOptions) {
        if (SortOptions.None == _uiState.value.sortOption && newSortOption == SortOptions.None) return //no-op

        println("Before sort first: ${_uiState.value.people.first().name}")

        val newList = when (newSortOption) {
            SortOptions.None -> {
                _uiState.value.people.sortedBy { person ->
                    person.url.split('/').last().toInt()
                }
            }

            SortOptions.Name -> {
                _uiState.value.people.sortedBy { person -> person.name }
            }

            SortOptions.Height -> {
                _uiState.value.people.sortedBy { person -> person.height }
            }

            SortOptions.Mass -> {
                _uiState.value.people.sortedBy { person -> person.mass }
            }

            SortOptions.VehicleCount -> {
                _uiState.value.people.sortedBy { person -> person.vehicleUrls.size + person.starshipUrls.size }
            }
        }

        if (newSortOption == _uiState.value.sortOption) {
            val newSortDirection = _uiState.value.sortDirection.getOpposite()
            println("$newSortOption: $newSortDirection")

            val listToUse =
                if (newSortDirection == SortDirections.High) newList.asReversed() else newList
            _uiState.update {
                it.copy(
                    people = listToUse,
                    sortDirection = newSortDirection
                )
            }

        } else {
            println("$newSortOption: ${SortDirections.Low}")
            _uiState.update {
                it.copy(
                    people = newList,
                    sortOption = newSortOption,
                    sortDirection = SortDirections.Low
                )
            }
        }
    }

    fun onHairColorFilterOptionSelected(selectedHairColorIndex: Int) {
        _uiState.update {
            it.copy(
                hairColorSelection = it.hairColorSelection.copy(currentSelection = selectedHairColorIndex)
            )
        }
    }

    fun onHairColorFilterOptionEnabledChange(isEnabled: Boolean) {
        _uiState.update {
            it.copy(
                hairColorSelection = it.hairColorSelection.copy(isEnabled = isEnabled)
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = true,
        val people: List<Person> = listOf(),
        val sortOption: SortOptions = SortOptions.None,
        val sortDirection: SortDirections = SortDirections.Low,
        val hairColorSelection: FilterOptionSelection = FilterOptionSelection(
            options = listOf("Option 1")
        )
    )

    data class FilterOptionSelection(
        val options: List<String>,
        val currentSelection: Int,
        val isEnabled: Boolean
    ) {
        constructor(options: List<String>) : this(
            options = options,
            currentSelection = 0,
            isEnabled = false
        )

        val selectedHairColor = options[currentSelection]
    }

    enum class SortOptions {
        None,
        Name,
        Height,
        Mass,
        VehicleCount,
    }

    enum class SortDirections {
        Low, // A to Z, low to high
        High // Z to A, high to low
    }

    enum class PersonFilterOptions {
        HairColor,
        SkinColor,
        EyeColor,
        Gender
    }

    private fun SortDirections.getOpposite(): SortDirections {
        return if (this == SortDirections.Low) SortDirections.High else SortDirections.Low
    }
}