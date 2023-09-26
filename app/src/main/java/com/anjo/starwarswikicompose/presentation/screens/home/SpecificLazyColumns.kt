package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun Movies(refreshing: Boolean, navController: NavHostController, films: GetAllFilmsQuery.AllFilms) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = films.films.orEmpty(),
            ) { index: Int, item: GetAllFilmsQuery.Film? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Peoples(refreshing: Boolean, navController: NavHostController, people: GetAllPeoplesQuery.AllPeople) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = people.people.orEmpty(),
            ) { index: Int, item: GetAllPeoplesQuery.Person? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Planets(refreshing: Boolean, navController: NavHostController, planets: GetAllPlanetsQuery.AllPlanets) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = planets.planets.orEmpty(),
            ) { index: Int, item: GetAllPlanetsQuery.Planet? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Species(refreshing: Boolean, navController: NavHostController, species: GetAllSpeciesQuery.AllSpecies) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = species.species.orEmpty(),
            ) { index: Int, item: GetAllSpeciesQuery.Species? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Starships(refreshing: Boolean, navController: NavHostController, starships: GetAllStarshipsQuery.AllStarships) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = starships.starships.orEmpty(),
            ) { index: Int, item: GetAllStarshipsQuery.Starship? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Vehicles(refreshing: Boolean, navController: NavHostController, vehicles: GetAllVehiclesQuery.AllVehicles) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = vehicles.vehicles.orEmpty(),
            ) { index: Int, item: GetAllVehiclesQuery.Vehicle? ->
                Button(onClick = { navController.navigate(Screen.Home.route) }) {
                    Row {
                        Text((index + 1).toString())
                        Text("    ", modifier = Modifier.background(Color.Green))
                        Text(item.toString())
                    }
                }
            }
        }
    }
}