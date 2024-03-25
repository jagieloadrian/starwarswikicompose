package com.anjo.starwarswikicompose.presentation.common.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.domain.model.sw.isError
import com.anjo.starwarswikicompose.domain.model.sw.isLoading
import com.anjo.starwarswikicompose.domain.model.sw.isSuccess
import com.anjo.starwarswikicompose.presentation.common.AddImageFabWrap
import com.anjo.starwarswikicompose.presentation.common.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.common.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.common.ErrorScreenWrapper
import com.anjo.starwarswikicompose.presentation.common.LoadingScreen
import com.anjo.starwarswikicompose.utils.Constants.CUSTOM_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.Constants.ERROR_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.Constants.ERROR_DESCRIPTION
import com.anjo.starwarswikicompose.utils.Constants.LOADING_ANIMATED_LABEL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailVisualisationComponent(
        refreshImages: () -> Unit,
        selectedName: String,
        saveInDatabase: (String) -> Unit,
        navController: NavHostController,
        stateObject: DetailObjectState,
        content: @Composable (ScrollState, CoroutineScope, SnackbarHostState, MutableState<Boolean>, Modifier) -> Unit,
) {
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val clipManager = LocalClipboardManager.current
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            refreshImages()
            delay(1500)
            isRefreshing = false
        }
    })
    val modifier = Modifier.pullRefresh(pullRefreshState)

    LaunchedEffect(imagesStateRefresh.value) {
        refreshImages()
        delay(1000)
        imagesStateRefresh.value = false
    }

    LaunchedEffect(state) {
        var prev = 0
        snapshotFlow { state.value }.collect {
            fabExtended = it <= prev
            prev = it
        }
    }

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            floatingActionButton = {
                AddImageFabWrap(fabExtended, clipManager, navController, selectedName, {
                    saveInDatabase(it)
                    imagesStateRefresh.value = true
                }, refreshScope, snackBarHostState, stateObject.isSuccess())
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center) {
            if (!isRefreshing) {
                DetailVisualisationState(stateObject, modifier)
                { content(state, refreshScope, snackBarHostState, imagesStateRefresh, modifier) }
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
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