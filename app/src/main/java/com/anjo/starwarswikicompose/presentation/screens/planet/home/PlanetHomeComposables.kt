package com.anjo.starwarswikicompose.presentation.screens.planet.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.presentation.screens.home.formatPopulation
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Category

@Composable
fun Planets(navController: NavHostController, homePlanetViewModel: HomePlanetViewModel = hiltViewModel(),
            refresh: Boolean) {
    val item by homePlanetViewModel.fetchedPlanets.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        }
        LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
            items(items = item.planets.orEmpty()) { item: GetAllPlanetsQuery.Planet? ->
                CommonButton(navController, item, Category.PLANETS)
            }
        }
        if (!refresh) {
            homePlanetViewModel.fetchPlanets()
        }
    }
}

@Composable
fun PlanetColumnText(item: GetAllPlanetsQuery.Planet?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = (formatPopulation(item?.population)))
    }
}