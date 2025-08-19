package com.yveltius.swapi.films

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yveltius.swapi.R

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
                items(uiState.films) { film ->
                    Text(text = film.title)
                }
            }
        }
    }
}