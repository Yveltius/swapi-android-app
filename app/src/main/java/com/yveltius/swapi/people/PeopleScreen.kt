package com.yveltius.swapi.people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yveltius.swapi.R
import com.yveltius.swapicore.entity.api.Person
import com.yveltius.swapicore.ext.fromJsonString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    peopleViewModel: PeopleViewModel = PeopleViewModel()
) {
    val uiState by peopleViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            PeopleTopAppBar(
                onNavigateUp = onNavigateUp,
                modalBottomSheetState = modalBottomSheetState,
                scope = scope
            )
        }
    ) { contentPadding ->
        if (modalBottomSheetState.currentValue != SheetValue.Hidden) {
            FilterBottomSheet(
                modalBottomSheetState = modalBottomSheetState,
                scope = scope,
                currentSortOption = uiState.sortOption,
                currentSortDirection = uiState.sortDirection,
                onSortOptionSelected = peopleViewModel::onSortOptionChanged,
                hairColorFilterOptions = uiState.hairColorSelection,
                onHairColorFilterSelected = peopleViewModel::onHairColorFilterOptionSelected,
                onHairColorFilterEnabledChange = peopleViewModel::onHairColorFilterOptionEnabledChange
            )
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            itemsIndexed(
                uiState.people
                    .filter { person ->
                        if (uiState.hairColorSelection.isEnabled) {
                            person.hairColor.contains(uiState.hairColorSelection.selectedHairColor, ignoreCase = true)
                        } else true
                    }
            ) { index, person ->
                PersonView(person = person, sortOption = uiState.sortOption)

                if (index < uiState.people.size - 1) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleTopAppBar(
    modalBottomSheetState: SheetState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = "People") },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = {
                scope.launch {
                    modalBottomSheetState.apply {
                        if (modalBottomSheetState.currentValue == SheetValue.Hidden) {
                            modalBottomSheetState.show()
                        } else {
                            modalBottomSheetState.hide()
                        }
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = ""
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    modalBottomSheetState: SheetState,
    scope: CoroutineScope,
    currentSortOption: PeopleViewModel.SortOptions,
    currentSortDirection: PeopleViewModel.SortDirections,
    onSortOptionSelected: (PeopleViewModel.SortOptions) -> Unit,
    hairColorFilterOptions: PeopleViewModel.FilterOptionSelection,
    onHairColorFilterSelected: (Int) -> Unit,
    onHairColorFilterEnabledChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = { scope.launch { modalBottomSheetState.hide() } },
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Sort",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            PeopleViewModel.SortOptions.entries.forEach { sortOption ->
                val isSelected = currentSortOption == sortOption
                FilterChip(
                    label = { Text(text = sortOption.getChipName()) },
                    selected = isSelected,
                    onClick = { onSortOptionSelected(sortOption) },
                    trailingIcon = {
                        if (isSelected && sortOption != PeopleViewModel.SortOptions.None) {
                            val icon =
                                if (currentSortDirection == PeopleViewModel.SortDirections.Low) {
                                    Icons.Default.ArrowDropDown
                                } else {
                                    Icons.Default.ArrowDropUp
                                }
                            Icon(imageVector = icon, contentDescription = "")
                        }
                    }
                )
            }
        }

        Text(
            text = "Filter",
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Filter(
            filterOption = hairColorFilterOptions,
            colorFilterSelected = onHairColorFilterSelected,
            onIsEnabledChange = onHairColorFilterEnabledChange)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Filter(
    filterOption: PeopleViewModel.FilterOptionSelection,
    colorFilterSelected: (Int) -> Unit,
    onIsEnabledChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expanded by remember { mutableStateOf(value = false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = filterOption.selectedHairColor,
                onValueChange = {
                    colorFilterSelected(filterOption.options.indexOf(it))
                    expanded = false
                },
                readOnly = true,
                label = { Text(text = "Hair Color") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                        modifier = Modifier.menuAnchor(type = MenuAnchorType.SecondaryEditable)
                    )
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                filterOption.options.forEachIndexed { index, hairColor ->
                    DropdownMenuItem(
                        text = { Text(text = hairColor, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            colorFilterSelected(index)
                            expanded = false
                        }
                    )
                }
            }
        }

        Checkbox(checked = filterOption.isEnabled, onCheckedChange = onIsEnabledChange)
    }
}

@Composable
fun PersonView(
    person: Person,
    sortOption: PeopleViewModel.SortOptions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = person.name, fontSize = 16.sp)
        val descriptionText = when (sortOption) {
            PeopleViewModel.SortOptions.None,
            PeopleViewModel.SortOptions.Name -> stringResource(R.string.url, person.url)

            PeopleViewModel.SortOptions.Height -> stringResource(
                R.string.height,
                if (person.height != -1) person.height else stringResource(R.string.not_applicable)
            )

            PeopleViewModel.SortOptions.Mass -> stringResource(
                R.string.mass,
                if (person.mass != -1) person.mass else stringResource(R.string.not_applicable)
            )

            PeopleViewModel.SortOptions.VehicleCount -> stringResource(
                R.string.vehicle_count,
                person.vehicleCount
            )
        }
        Text(text = descriptionText, fontSize = 14.sp)
    }
}

@Composable
fun PeopleViewModel.SortOptions.getChipName() = when (this) {
    PeopleViewModel.SortOptions.Name -> stringResource(R.string.name_sort)
    PeopleViewModel.SortOptions.None -> stringResource(R.string.no_sort)
    PeopleViewModel.SortOptions.Height -> stringResource(R.string.height_sort)
    PeopleViewModel.SortOptions.Mass -> stringResource(R.string.mass_sort)
    PeopleViewModel.SortOptions.VehicleCount -> stringResource(R.string.vehicle_count_sort)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun TopAppBarPreview() {
    PeopleTopAppBar(
        modalBottomSheetState = rememberModalBottomSheetState(),
        scope = rememberCoroutineScope()
    )
}

@Preview(showBackground = true)
@Composable
private fun PersonViewPreview() {
    val testPerson: Person =
        "{\"name\":\"Luke Skywalker\",\"height\":\"172\",\"mass\":\"77\",\"hair_color\":\"blond\",\"skin_color\":\"fair\",\"eye_color\":\"blue\",\"birth_year\":\"19BBY\",\"gender\":\"male\",\"homeworld\":\"https://swapi.info/api/planets/1\",\"films\":[\"https://swapi.info/api/films/1\",\"https://swapi.info/api/films/2\",\"https://swapi.info/api/films/3\",\"https://swapi.info/api/films/6\"],\"species\":[],\"vehicles\":[\"https://swapi.info/api/vehicles/14\",\"https://swapi.info/api/vehicles/30\"],\"starships\":[\"https://swapi.info/api/starships/12\",\"https://swapi.info/api/starships/22\"],\"created\":\"2014-12-09T13:50:51.644000Z\",\"edited\":\"2014-12-20T21:17:56.891000Z\",\"url\":\"https://swapi.info/api/people/1\"}".fromJsonString<Person>()
            .getOrThrow()

    PersonView(person = testPerson, sortOption = PeopleViewModel.SortOptions.None)
}