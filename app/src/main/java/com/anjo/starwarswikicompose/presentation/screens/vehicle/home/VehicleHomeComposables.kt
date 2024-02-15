package com.anjo.starwarswikicompose.presentation.screens.vehicle.home

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
fun Vehicles(
        navController: NavHostController,
        homeVehicleViewModel: HomeVehicleViewModel = hiltViewModel(),
        refresh: Boolean) {
    val item by homeVehicleViewModel.fetchedVehicles.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
       homeVehicleViewModel.getVehicles()
        init.value = false
    }

    HomeVehicleContent(item, navController)
        if (!refresh) {
            homeVehicleViewModel.fetchVehicles()
        }
    }

@Composable
fun HomeVehicleContent(
        item: HomeVehicleViewModel.VehicleState,
        navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                items(items = item.vehicles) { item ->
                    CommonButton(navController, item, Category.VEHICLES)
                }
            }
        }
    }
}
