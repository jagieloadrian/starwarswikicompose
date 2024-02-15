package com.anjo.starwarswikicompose.presentation.screens.common.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.presentation.screens.common.AddImageFabWrap
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun DetailVisualisationComponent(
        refreshImages: () -> Unit,
        selectedName: String,
        saveInDatabase: (String) -> Unit,
        navController: NavHostController,
        content: @Composable (PaddingValues, ScrollState, CoroutineScope, SnackbarHostState, MutableState<Boolean>) -> Unit,
) {
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val clipManager = LocalClipboardManager.current
    val refreshScope = rememberCoroutineScope()

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
                }, refreshScope, snackBarHostState)
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        content(padding, state, refreshScope, snackBarHostState, imagesStateRefresh)
    }
}