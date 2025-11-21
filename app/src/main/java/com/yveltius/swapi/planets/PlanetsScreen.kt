package com.yveltius.swapi.planets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yveltius.swapi.ext.upperFirstChar
import com.yveltius.swapicore.entity.api.Planet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetsScreen(
    onNavigateUp: () -> Unit,
    planetsViewModel: PlanetsViewModel = PlanetsViewModel()
) {
    val uiState by planetsViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Planets")},
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = uiState.planets, key = { index, planet -> planet.name } ) { index, planet ->
                PlanetItem(
                    planet = planet,
                    modifier = Modifier.fillMaxWidth()
                )

                if (index < (uiState.planets.size - 1)) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = planet.name, fontSize = 20.sp)
        Text(
            text = planet.terrain
                .split(',')
                .map { it.trim() }
                .joinToString { it.upperFirstChar() }
        )
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    PlanetsScreen(
        onNavigateUp = {}
    )
}