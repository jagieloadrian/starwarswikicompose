package com.anjo.starwarswikicompose.presentation.screens.specie.home

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
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Category

@Composable
fun Species(navController: NavHostController, homeSpecieViewModel: HomeSpecieViewModel = hiltViewModel(),
            refresh: Boolean) {
    val item by homeSpecieViewModel.fetchedSpecies.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                items(items = item.species.orEmpty()) { item: GetAllSpeciesQuery.Species? ->
                    CommonButton(navController, item, Category.SPECIES)
                }
            }
        }
        if (!refresh) {
            homeSpecieViewModel.fetchSpecies()
        }
    }
}

@Composable
fun SpecieColumnText(item: GetAllSpeciesQuery.Species?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = item?.language.toString())
    }
}