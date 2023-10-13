package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.FILMS

@Composable
fun Movies(navController: NavHostController, films: GetAllFilmsQuery.AllFilms) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = films.films.orEmpty()) { film ->
            CommonButton(navController, film, FILMS)
        }
    }
}

@Composable
fun People(navController: NavHostController, people: GetAllPeoplesQuery.AllPeople) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = people.people.orEmpty()) { item: GetAllPeoplesQuery.Person? ->
            CommonButton(navController, item, Category.PEOPLE)
        }
    }
}


@Composable
fun Planets(navController: NavHostController, planets: GetAllPlanetsQuery.AllPlanets) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = planets.planets.orEmpty()) { item: GetAllPlanetsQuery.Planet? ->
            CommonButton(navController, item, Category.PLANETS)
        }
    }
}


@Composable
fun Species(navController: NavHostController, species: GetAllSpeciesQuery.AllSpecies) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = species.species.orEmpty()) { item: GetAllSpeciesQuery.Species? ->
            CommonButton(navController, item, Category.SPECIES)
        }
    }
}


@Composable
fun Starships(navController: NavHostController, starships: GetAllStarshipsQuery.AllStarships) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = starships.starships.orEmpty()) { item: GetAllStarshipsQuery.Starship? ->
            CommonButton(navController, item, Category.STARSHIPS)
        }
    }
}


@Composable
fun Vehicles(navController: NavHostController, vehicles: GetAllVehiclesQuery.AllVehicles) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        items(items = vehicles.vehicles.orEmpty()) { item: GetAllVehiclesQuery.Vehicle? ->
            CommonButton(navController, item, Category.VEHICLES)
        }
    }
}
