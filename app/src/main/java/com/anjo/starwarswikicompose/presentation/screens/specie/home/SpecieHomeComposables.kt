package com.anjo.starwarswikicompose.presentation.screens.specie.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun Species(
        navController: NavHostController, homeSpecieViewModel: HomeSpecieViewModel = hiltViewModel(),
        refresh: Boolean,
) {
    val item by homeSpecieViewModel.fetchedSpecies.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        homeSpecieViewModel.getSpecies()
        init.value = false
    }

    HomeSpecieContent(item, navController)
        if (!refresh) {
            homeSpecieViewModel.fetchSpecies()
        }
    }

@Composable
fun HomeSpecieContent(
        item: HomeSpecieViewModel.SpecieState,
        navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                items(items = item.species) { item ->
                    CommonButton(navController, item, Category.SPECIES)
                }
            }
        }
    }
}
