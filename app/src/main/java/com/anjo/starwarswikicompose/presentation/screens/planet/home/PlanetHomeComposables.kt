package com.anjo.starwarswikicompose.presentation.screens.planet.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.common.SearchBarForChunks
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.filterItems

@Composable
fun Planets(
        navController: NavHostController, homePlanetViewModel: HomePlanetViewModel = hiltViewModel(),
        refresh: Boolean,
) {
    val item by homePlanetViewModel.fetchedPlanets.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        homePlanetViewModel.getPlanets()
        init.value = false
    }

    HomePlanetContent(item, navController)
    if (!refresh) {
        homePlanetViewModel.fetchPlanets()
    }
}

@Composable
fun HomePlanetContent(
        item: HomePlanetViewModel.PlanetState,
        navController: NavHostController,
) {
    var textState by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    var enabled by remember { mutableStateOf(true) }
    var planets by remember { mutableStateOf(item.planets) }
    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            Column {
                SearchBarForChunks(text = textState,
                        onTextChange = { query ->
                            textState = query
                        },
                        onClosedClicked = { enabled = false },
                        enabled = enabled,
                        lazyListState = lazyListState,
                        modifier = Modifier.clickable {
                            if (!enabled) {
                                enabled = true
                            }
                        }
                                .background(Color.Transparent),
                        placeholder = ""
                )
                LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                    val searchedText = textState
                    planets = if (textState.isBlank()) {
                        item.planets
                    } else {
                        filterItems(searchedText, item.planets)
                    }
                    items(items = planets) { item ->
                        CommonButton(navController, item, Category.PLANETS)
                    }
                }
            }
        }
    }
}
