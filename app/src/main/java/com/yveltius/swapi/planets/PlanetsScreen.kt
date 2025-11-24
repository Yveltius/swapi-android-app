package com.yveltius.swapi.planets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yveltius.swapi.R
import com.yveltius.swapicore.ext.upperFirstChar
import com.yveltius.swapi.ui.common.FullscreenLoadingIndicator
import com.yveltius.swapi.ui.common.RetryView
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
                title = { Text(text = "Planets") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                })
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                FullscreenLoadingIndicator()
            }

            uiState.hasError -> {
                RetryView(
                    explanation = stringResource(R.string.failed_to_get_planets),
                    onRetry = planetsViewModel::getPlanets,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                PlanetList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = innerPadding)
                        .padding(16.dp),
                    planets = uiState.planets
                )
            }
        }
    }
}

@Composable
fun PlanetList(
    planets: List<Planet>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = planets, key = { index, planet -> planet.name }) { index, planet ->
            PlanetItem(
                planet = planet,
                modifier = Modifier.fillMaxWidth()
            )

            if (index < (planets.size - 1)) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
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
        Text(text = stringResource(R.string.planet_climate, planet.formattedClimate))
        Text(text = stringResource(R.string.planet_terrain, planet.formattedTerrain))
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    PlanetsScreen(
        onNavigateUp = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PlanetListPreview() {
    PlanetList(
        planets = List(12) {
            Planet(
                name = "Test Planet $it",
                _rotationPeriod = "TODO()",
                _orbitalPeriod = "TODO()",
                _diameter = "TODO()",
                climate = "Hot",
                gravity = "1 standard",
                terrain = "hills",
                _surfaceWater = "1",
                _population = "TODO()",
                residentUrls = listOf(),
                filmUrls = listOf(),
                created = "TODO()",
                edited = "TODO()",
                url = "TODO()"
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}