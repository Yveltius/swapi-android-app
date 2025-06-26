package com.yveltius.swapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yveltius.swapi.people.PeopleScreen
import com.yveltius.swapi.ui.theme.SWAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SWAPITheme {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.Start.name
                ) {
                    composable(route = Destinations.Start.name) {
                        Greeting("Start Screen", onPeopleScreenSelected = {
                            navController.navigate(Destinations.People.name)
                        })
                    }

                    composable(route = Destinations.People.name) {
                        PeopleScreen(modifier = Modifier.fillMaxSize(), onNavigateUp = { navController.navigateUp() })
                    }
                }
            }
        }
    }
}

enum class Destinations {
    Start,
    People
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onPeopleScreenSelected: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = onPeopleScreenSelected) {
            Text(text = "People")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SWAPITheme {
        Greeting("Android")
    }
}