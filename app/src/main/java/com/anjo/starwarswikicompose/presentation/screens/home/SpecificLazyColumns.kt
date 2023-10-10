package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.navigation.NavHostController
import com.anjo.*
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Category

@Composable
fun Movies(refreshing: Boolean, navController: NavHostController, films: GetAllFilmsQuery.AllFilms) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = films.films.orEmpty()
            ) { index: Int, item: GetAllFilmsQuery.Film? ->
                CommonButton(navController, item, Category.FILMS)
            }
        }
    }
}

@Composable
fun People(refreshing: Boolean, navController: NavHostController, people: GetAllPeoplesQuery.AllPeople) {
    LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        if (!refreshing) {
            itemsIndexed(
                    items = people.people.orEmpty(),
            ) { index: Int, item: GetAllPeoplesQuery.Person? ->
                CommonButton(navController, item, Category.PEOPLE)
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
                CommonButton(navController, item, Category.PLANETS)
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
                CommonButton(navController, item, Category.SPECIES)
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
                CommonButton(navController, item, Category.STARSHIPS)
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
                CommonButton(navController, item, Category.VEHICLES)
            }
        }
    }
}