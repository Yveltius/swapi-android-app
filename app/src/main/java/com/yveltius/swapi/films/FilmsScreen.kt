package com.yveltius.swapi.films

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
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
import com.yveltius.swapicore.entity.api.Film

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    filmsViewModel: FilmsViewModel = FilmsViewModel()
) {
    val uiState by filmsViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Films")},
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                })
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(uiState.films) { index, film ->
                    FilmItem(film = film, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))

                    if (index < (uiState.films.size - 1)) {
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun FilmList(modifier: Modifier = Modifier) {

}

@Composable
fun FilmItem(
    film: Film,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = film.title, fontSize = 20.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = stringResource(R.string.film_characters, film.characterUrls.size))
                Text(text = stringResource(R.string.film_species, film.speciesUrls.size))
                Text(text = stringResource(R.string.film_planets, film.planetUrls.size))
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = stringResource(R.string.film_starships, film.starshipUrls.size))
                Text(text = stringResource(R.string.film_vehicles, film.vehicleUrls.size))
            }
        }

        Text(text = film.url, fontSize = 10.sp)
    }
}

@Composable
fun LoadingFilms(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun ScreenPreview() {
    FilmsScreen(
        onNavigateUp = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(showBackground = true)
@Composable
private fun FilmViewPreview() {
    val film = Film(
        title = "Movie Title: It is a Title",
        episodeId = 5,
        openingCrawl = "BIG OLE TEXT",
        director = "Great Guy",
        producer = "Other Great Guy",
        releaseDate = "01/01/2021",
        characterUrls = List(16) { it.toString() },
        planetUrls = List(6) { it.toString() },
        starshipUrls = List(6) { it.toString() },
        vehicleUrls = List(6) { it.toString() },
        speciesUrls = List(6) { it.toString() },
        created = "TODO()",
        edited = "TODO()",
        url = "https://www.thisisatodourl.com/endpoint"
    )

    FilmItem(film, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp))
}