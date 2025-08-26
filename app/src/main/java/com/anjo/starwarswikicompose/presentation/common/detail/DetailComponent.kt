package com.anjo.starwarswikicompose.presentation.common.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.domain.model.sw.isError
import com.anjo.starwarswikicompose.domain.model.sw.isLoading
import com.anjo.starwarswikicompose.domain.model.sw.isSuccess
import com.anjo.starwarswikicompose.presentation.common.DetailObjectFabMenu
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.common.errorempty.ErrorScreenWrapper
import com.anjo.starwarswikicompose.presentation.common.loading.LoadingScreen
import com.anjo.starwarswikicompose.utils.Constants.ERROR_DESCRIPTION
import com.anjo.starwarswikicompose.utils.TestTags.CUSTOM_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.TestTags.ERROR_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.TestTags.LOADING_ANIMATED_LABEL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailVisualisationComponent(
        refreshObject: () -> Unit,
        saveImageInDatabase: (String) -> Unit,
        updateObjectFab: () -> Unit,
        navController: NavHostController,
        stateObject: DetailObjectState,
        removeObjectHandler: Pair<Boolean, () -> Unit>,
        content: @Composable (ScrollState, CoroutineScope, SnackbarHostState, MutableState<Boolean>, Modifier) -> Unit,
) {
    val state = rememberScrollState()
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val refreshScope = rememberCoroutineScope()
    val isRefreshing = remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()
    val onRefresh = {
        isRefreshing.value = true
        refreshScope.launch {
            refreshObject()
            delay(1500)
        }
        isRefreshing.value = false
    }
    val modifier = Modifier.pullToRefresh(isRefreshing.value, pullRefreshState, onRefresh = onRefresh)

    LaunchedEffect(imagesStateRefresh.value) {
        refreshObject()
        delay(1000)
        imagesStateRefresh.value = false
    }

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            floatingActionButton = {
                if (stateObject.isSuccess()) {
                    DetailObjectFabMenu(navController, removeObjectHandler.first,
                            saveImageInDatabase, removeObjectHandler.second, updateObjectFab)
                }
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center) {
            if (!isRefreshing.value) {
                DetailVisualisationState(stateObject, modifier)
                { content(state, refreshScope, snackBarHostState, imagesStateRefresh, modifier) }
            }
            Indicator(pullRefreshState, isRefreshing.value, Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun DetailVisualisationState(
        stateObject: DetailObjectState,
        modifier: Modifier,
        content: @Composable () -> Unit,
) {
    AnimatedVisibility(stateObject.isSuccess(), enter = scaleIn(), exit = scaleOut(), label = CUSTOM_ANIMATED_LABEL) {
        content()
    }

    AnimatedVisibility(stateObject.isError(), enter = scaleIn(), exit = scaleOut(), label = ERROR_ANIMATED_LABEL) {
        ErrorScreenWrapper(ERROR_DESCRIPTION, modifier)
    }

    AnimatedVisibility(stateObject.isLoading(), enter = scaleIn(), exit = scaleOut(), label = LOADING_ANIMATED_LABEL) {
        LoadingScreen()
    }
}