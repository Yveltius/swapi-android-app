package com.yveltius.swapi.films

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                        Icon(
                            painter = painterResource(
                                R.drawable.ic_launcher_foreground
                            ),
                            contentDescription = null
                        )
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
                    FilmView(film = film, modifier = Modifier.fillMaxWidth())

                    if (index < (uiState.films.size - 1)) {
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun FilmView(
    film: Film,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(160.dp).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Text(text = film.title, fontSize = 20.sp, modifier = Modifier.align(Alignment.Center))

            Text(text = film.url, fontSize = 10.sp, modifier = Modifier.align(Alignment.BottomStart))
        }

        Column(
            modifier = Modifier.weight(0.4f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = stringResource(R.string.film_characters, film.characterUrls.size))
            Text(text = stringResource(R.string.film_planets, film.planetUrls.size))
            Text(text = stringResource(R.string.film_starships, film.starshipUrls.size))
            Text(text = stringResource(R.string.film_vehicles, film.vehicleUrls.size))
            Text(text = stringResource(R.string.film_species, film.speciesUrls.size))
        }
    }

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

    FilmView(film, modifier = Modifier.fillMaxWidth())
}