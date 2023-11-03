package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GallerySlider(
        images: List<ImageSliderModel>,
        onCLickLeft: () -> Unit,
        onCLickRight: (ImageSliderModel) -> Unit,
) {
    val loadingBoxVisible = remember { mutableStateOf(false) }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val maxWidth = getLocalWidth().dp
    val density = LocalDensity.current

    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        refreshScope.launch {
            delay(1500)
            isRefreshing = false
        }
    }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = onRefresh)

    LaunchedEffect(loadingBoxVisible.value) {
        delay(1.seconds)
        loadingBoxVisible.value = false
    }

    if (loadingBoxVisible.value) {
        Box(modifier = Modifier,
                contentAlignment = Alignment.Center) {
            LoadingBox()
        }
    }

    if (images.isNotEmpty()) {
        Card(modifier = Modifier.padding(SMALL_PADDING)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                },
                shape = RoundedCornerShape(MEDIUM_PADDING)) {
            if (!isRefreshing) {
                ImageCarousel(images) { index ->
                    Box(modifier = Modifier
                            .fillMaxWidth()) {
                        AsyncImage(
                                model = images[index].url,
                                placeholder = painterResource(R.drawable.image_icon),
                                error = painterResource(R.drawable.ic_network_error),
                                contentDescription = stringResource(R.string.flickr_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.height(PICTURE_HEIGHT)
                                        .width(maxWidth)
                        )
                        Surface(modifier = Modifier.background(Color.Transparent)
                                .align(Alignment.TopStart),
                                color = Color.Transparent) {
                            CornerButton(imageVector = Icons.Filled.Refresh) {
                                onRefresh()
                                onCLickLeft() }

                        }
                        Surface(modifier = Modifier.background(Color.Transparent)
                                .align(Alignment.TopEnd),
                                color = Color.Transparent) {
                            CornerButton(imageVector = Icons.Filled.Delete) {
                                onCLickRight(images[index])
                                onRefresh()
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier,
                    contentAlignment = Alignment.Center) {
                PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier
                        .align(Alignment.Center),
                        scale = true,
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.topAppBarHomeBackgroundColor)
            }
        }
    } else {
        loadingBoxVisible.value = true
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
        items: List<ImageSliderModel>,
        modifier: Modifier = Modifier,
        itemContent: @Composable (index: Int) -> Unit,
) {
    val pagerState = rememberPagerState { items.size }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(state = pagerState, key = { index -> items[index].id!! }) { page ->
            itemContent(page)
        }
        Surface(
                modifier = Modifier
                        .padding(EXTRA_SMALL_PADDING)
                        .align(Alignment.BottomCenter),
                shape = CircleShape,
                color = Color.Black.copy(0.5f)) {
            DotsIndicator(
                    totalDots = items.size,
                    selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage)
        }
    }
}
