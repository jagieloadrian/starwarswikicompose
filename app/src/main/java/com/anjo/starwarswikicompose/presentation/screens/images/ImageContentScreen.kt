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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.anjo.starwarswikicompose.domain.model.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.FlickrStatus
import com.anjo.starwarswikicompose.presentation.common.EmptyScreen
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ImageScreen(
        navHostController: NavHostController,
        imageViewModel: ImageViewModel = hiltViewModel()
) {

    val searchQuery by imageViewModel.searchQuery
    val photoResponse by imageViewModel.fetchedPhotoInfos.collectAsState()
    val enabled = remember { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()

    val extractPhotos = photoResponse.photos?.photo

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() {
        refreshScope.launch {
            refreshing = true
            if (searchQuery.isEmpty()) {
                imageViewModel.fetchRecentPhotos()
            } else {
                imageViewModel.fetchPhotoInfo(searchQuery)
            }
            delay(2500)
            refreshing = false
        }
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) ContentAlpha.high else 0f,
            animationSpec = tween(
                    durationMillis = 2000
            )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    Box(modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize()
            .alpha(alphaAnim)) {
        if (!refreshing) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .paint(painter = painterResource(R.drawable.stars_image),
                            contentScale = ContentScale.FillBounds)
            ) {
                SearchBar(text = searchQuery,
                        onTextChange = { imageViewModel.updateSearchQuery(query = it) },
                        onSearchClicked = { imageViewModel.fetchPhotoInfo(it) },
                        onClosedClicked = {
                            enabled.value = false
                        },
                        enabled = enabled.value,
                        lazyListState = lazyListState,
                        modifier = Modifier.clickable {
                            if (!enabled.value) {
                                enabled.value = true
                            }
                        })
                if (photoResponse.stat == FlickrStatus.fail) {
                    EmptyScreen(null, text = "images")
                } else {
                    extractPhotos?.let { LazyColumnPhotos(extractPhotos, lazyListState) }
                }
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun LazyColumnPhotos(
        photos: List<FlickrPhoto>,
        lazyListState: LazyListState) {
    LazyColumn(state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LARGE_PADDING),
            contentPadding = PaddingValues(all = SMALL_PADDING)) {
        items(photos) { photo ->
            ImageBox(photo)
        }
    }
}