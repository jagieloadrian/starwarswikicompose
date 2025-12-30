package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.isError
import com.anjo.starwarswikicompose.domain.model.flickr.isFail
import com.anjo.starwarswikicompose.domain.model.flickr.isLoading
import com.anjo.starwarswikicompose.domain.model.flickr.isOk
import com.anjo.starwarswikicompose.presentation.common.SearchBar
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.common.errorempty.EmptyScreen
import com.anjo.starwarswikicompose.presentation.common.errorempty.ErrorScreen
import com.anjo.starwarswikicompose.presentation.common.loading.LoadingScreen
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.COPIED_TO_CLIPBOARD
import com.anjo.starwarswikicompose.utils.TestTags.CUSTOM_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.TestTags.ERROR_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.TestTags.FAIL_ANIMATED_LABEL
import com.anjo.starwarswikicompose.utils.TestTags.LOADING_ANIMATED_LABEL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImageScreen(
        navController: NavHostController,
        imageViewModel: ImageViewModel = hiltViewModel(),
) {
    val init = remember { mutableStateOf(true) }
    if (init.value) {
        imageViewModel.getRecentPhotos()
        init.value = false
    }
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            topBar = { CustomTopAppBar(navHostController = navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        ImageGalleryVisualisation(padding, imageViewModel, snackBarHostState)
    }
}

@Composable
fun ImageGalleryVisualisation(
        padding: PaddingValues,
        imageViewModel: ImageViewModel,
        snackBarHostState: SnackbarHostState,
) {
    val photoResponse by imageViewModel.fetchedPhotoInfos.collectAsState()
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(
                    durationMillis = 2000
            ), label = ""
    )
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    LaunchedEffect(photoResponse) {
        lazyListState.animateScrollToItem(0)
    }

    ImagesContentVisualisation(padding, alphaAnim, imageViewModel, photoResponse, snackBarHostState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImagesContentVisualisation(
        padding: PaddingValues,
        alphaAnim: Float,
        imageViewModel: ImageViewModel,
        photoResponse: FlickrResponse,
        snackBarHostState: SnackbarHostState,
) {
    val searchQuery by imageViewModel.searchQuery
    val lazyListState = rememberLazyListState()
    var enabled by remember { mutableStateOf(true) }
    val refreshScope = rememberCoroutineScope()
    val refreshing = remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val onRefresh = { refresh(refreshScope, refreshing, searchQuery, imageViewModel) }

    val itemSize = 550.dp
    val density = LocalDensity.current
    val itemSizePx = with(density) { itemSize.toPx() }

    Box(modifier = Modifier
            .padding(padding)
            .pullToRefresh(refreshing.value, state, onRefresh = onRefresh)
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)
            .alpha(alphaAnim)) {
        if (!refreshing.value) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                SearchBar(text = searchQuery,
                        onTextChange = { imageViewModel.updateSearchQuery(query = it) },
                        onSearchClicked = { query ->
                            if (query.isEmpty()) {
                                imageViewModel.fetchRecentPhotos()
                                refreshScope.launch {
                                    delay(500)
                                    lazyListState.animateScrollBy(
                                            value = -(itemSizePx * lazyListState.firstVisibleItemIndex),
                                            animationSpec = tween(3000)
                                    )
                                }
                            } else {
                                imageViewModel.fetchPhotoInfo(query)
                                refreshScope.launch {
                                    delay(500)
                                    lazyListState.animateScrollBy(
                                            value = -(itemSizePx * lazyListState.firstVisibleItemIndex),
                                            animationSpec = tween(3000)
                                    )
                                }
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
                        },
                        placeholder = stringResource(R.string.placeholder_search))
                PhotoContent(photoResponse, lazyListState, snackBarHostState, refreshScope)
            }
        }
        Indicator(state, refreshing.value, Modifier.align(Alignment.TopCenter))
    }
}


@Composable
fun PhotoContent(
        photoResponse: FlickrResponse,
        lazyListState: LazyListState,
        snackBarHostState: SnackbarHostState,
        refreshScope: CoroutineScope,
) {
    val status = photoResponse.stat
    val extractPhotos = photoResponse.photos?.photo

    AnimatedVisibility(status.isOk(), enter = scaleIn(), exit = scaleOut(), label = CUSTOM_ANIMATED_LABEL) {
        extractPhotos?.let {
            LazyColumnPhotos(it, lazyListState) {
                refreshScope.launch {
                    snackBarHostState.showSnackbar(COPIED_TO_CLIPBOARD)
                }
            }
        } ?: EmptyScreen(null, "images")
    }

    AnimatedVisibility(status.isError(), enter = scaleIn(), exit = scaleOut(),
            label = ERROR_ANIMATED_LABEL) {
        ErrorScreen(photoResponse.message)
    }

    AnimatedVisibility(status.isFail(), enter = scaleIn(), exit = scaleOut(),
            label = FAIL_ANIMATED_LABEL) {
        EmptyScreen(null, text = "images")
    }

    AnimatedVisibility(status.isLoading(), enter = scaleIn(), exit = scaleOut(),
            label = LOADING_ANIMATED_LABEL) {
        LoadingScreen()
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

fun refresh(
        scope: CoroutineScope,
        refreshing: MutableState<Boolean>,
        searchQuery: String,
        imageViewModel: ImageViewModel,
) {
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