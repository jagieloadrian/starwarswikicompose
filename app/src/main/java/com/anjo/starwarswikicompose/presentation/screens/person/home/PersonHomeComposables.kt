package com.anjo.starwarswikicompose.presentation.screens.person.home

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
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.presentation.common.SearchBarForChunks
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.filterItems

@Composable
fun People(
        navController: NavHostController,
        homePersonViewModel: HomePersonViewModel = hiltViewModel(),
        refresh: Boolean,
) {
    val item by homePersonViewModel.fetchedPeople.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        homePersonViewModel.getPeople()
        init.value = false
    }

    HomePeopleContent(item, navController)
    if (!refresh) {
        homePersonViewModel.fetchPeople()
    }
}

@Composable
fun HomePeopleContent(
        item: HomePersonViewModel.PeopleState,
        navController: NavHostController,
) {
    var textState by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    var enabled by remember { mutableStateOf(true) }
    var people by remember { mutableStateOf(item.people) }
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
                    people = if (textState.isBlank()) {
                        item.people
                    } else {
                        filterItems(searchedText, item.people)
                    }
                    items(items = people) { item: UniversalChunk ->
                        CommonButton(navController, item, Category.PEOPLE)
                    }
                }
            }
        }
    }
}