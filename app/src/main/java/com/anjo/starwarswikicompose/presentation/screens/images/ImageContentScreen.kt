package com.anjo.starwarswikicompose.presentation.screens.images

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import com.anjo.starwarswikicompose.presentation.common.EmptyScreen
import com.anjo.starwarswikicompose.presentation.common.ErrorScreen
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.COPIED_TO_CLIPBOARD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ImageScreen(
        navController: NavHostController,
        imageViewModel: ImageViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        ImageGalleryVisualisation(padding,  imageViewModel, snackBarHostState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageGalleryVisualisation(
        padding: PaddingValues,
        imageViewModel: ImageViewModel,
        snackBarHostState: SnackbarHostState,
) {
    val searchQuery by imageViewModel.searchQuery
    val photoResponse by imageViewModel.fetchedPhotoInfos.collectAsState()
    val extractPhotos = photoResponse.photos?.photo
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) ContentAlpha.high else 0f,
            animationSpec = tween(
                    durationMillis = 2000
            ), label = ""
    )
    val lazyListState = rememberLazyListState()
    var enabled by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    LaunchedEffect(photoResponse) {
        lazyListState.animateScrollToItem(0)
    }
    val refreshScope = rememberCoroutineScope()
    val refreshing = remember { mutableStateOf(false) }
    val state =
        rememberPullRefreshState(refreshing.value, { refresh(refreshScope, refreshing, searchQuery, imageViewModel) })


    Box(modifier = Modifier
            .padding(padding)
            .pullRefresh(state)
            .fillMaxSize()
            .alpha(alphaAnim)) {
        if (!refreshing.value) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .paint(painter = painterResource(R.drawable.stars_image),
                            contentScale = ContentScale.FillBounds)
            ) {
                SearchBar(text = searchQuery,
                        onTextChange = { imageViewModel.updateSearchQuery(query = it) },
                        onSearchClicked = { query ->
                            if (query.isEmpty()) {
                                imageViewModel.fetchRecentPhotos()
                            } else {
                                imageViewModel.fetchPhotoInfo(query)
                            }
                        },
                        onClosedClicked = {
                            enabled = false
                        },
                        enabled = enabled,
                        lazyListState = lazyListState,
                        modifier = Modifier.clickable {
                            if (!enabled) {
                                enabled = true
                            }
                        })
                when (photoResponse.stat) {
                    FlickrStatus.error -> ErrorScreen(photoResponse.message)
                    FlickrStatus.fail  -> EmptyScreen(null, text = "images")
                    FlickrStatus.ok    -> extractPhotos?.let {
                        LazyColumnPhotos(extractPhotos,
                                lazyListState) {
                            refreshScope.launch {
                                snackBarHostState.showSnackbar(COPIED_TO_CLIPBOARD)
                            }
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(refreshing.value, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun LazyColumnPhotos(
        photos: List<FlickrPhoto>,
        lazyListState: LazyListState,
        addCopyAction: () -> Unit,
) {
    LazyColumn(state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LARGE_PADDING),
            contentPadding = PaddingValues(all = SMALL_PADDING)) {
        items(photos) { photo ->
            ImageBox(photo = photo) { addCopyAction() }
        }
    }
}

fun refresh(scope: CoroutineScope,
        refreshing: MutableState<Boolean>,
        searchQuery: String,
        imageViewModel: ImageViewModel) {
    scope.launch {
        refreshing.value = true
        if (searchQuery.isEmpty()) {
            imageViewModel.fetchRecentPhotos()
        } else {
            imageViewModel.fetchPhotoInfo(searchQuery)
        }
        delay(2500)
        refreshing.value = false
    }
}