package com.anjo.starwarswikicompose.presentation.common

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.presentation.common.button.CornerButton
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalWidth
import com.anjo.starwarswikicompose.presentation.common.loading.LoadingBox
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.Constants.MEDIUM_WHITE_BACKGROUND_COPY
import com.anjo.starwarswikicompose.utils.Constants.PHOTO_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun GallerySliderPart(
        imagesState: List<ImageSliderModel>,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        removeFromDatabase: (ImageSliderModel) -> Unit) {
    ConnectionTitle(text = PHOTO_NAME, shouldShowTitle = imagesState.isNotEmpty())
    GallerySlider(images = imagesState,
            onCLickLeft = {
                refreshScope.launch {
                    snackBarHostState.showSnackbar(Constants.REFRESH_IMAGES)
                }
                imagesStateRefresh.value = true
            },
            onCLickRight = {
                refreshScope.launch {
                    snackBarHostState.showSnackbar(Constants.DELETE_AND_REFRESH_IMAGES)
                }
                removeFromDatabase(it)
                imagesStateRefresh.value = true
            })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GallerySlider(
        images: List<ImageSliderModel>,
        onCLickLeft: () -> Unit,
        onCLickRight: (ImageSliderModel) -> Unit,
) {
    val loadingBoxVisible = remember { mutableStateOf(false) }
    val maxWidth = getLocalWidth().dp

    LaunchedEffect(loadingBoxVisible.value) {
        delay(2.seconds)
        loadingBoxVisible.value = false
    }

    if (loadingBoxVisible.value) {
        LoadingBox()
    }

    if (images.isNotEmpty()) {
        Card(modifier = Modifier
                .padding(SMALL_PADDING),
                shape = RoundedCornerShape(MEDIUM_PADDING)) {
            ImageCarousel(images) { index ->
                ImageBox(images, index, maxWidth, onCLickLeft, onCLickRight)
            }
        }
    } else {
        loadingBoxVisible.value = true
    }
}

@Composable
fun ImageBox(
        images: List<ImageSliderModel>,
        index: Int, maxWidth: Dp,
        onCLickLeft: () -> Unit,
        onCLickRight: (ImageSliderModel) -> Unit,
) {
    val currentImage = images[index]
    Box(modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
                model = currentImage.url,
                placeholder = painterResource(R.drawable.image_icon),
                error = painterResource(R.drawable.ic_network_error),
                contentDescription = "${stringResource(R.string.flickr_image)} ${currentImage.objectId}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                        .height(PICTURE_HEIGHT)
                        .background(Color.White.copy(MEDIUM_WHITE_BACKGROUND_COPY))
                        .width(maxWidth)
        )
        Surface(modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.TopStart),
                color = Color.Transparent) {
            CornerButton(imageVector = Icons.Filled.Refresh, contentDescription = "onClickLeft") {
                onCLickLeft()
            }

        }
        Surface(modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.TopEnd),
                color = Color.Transparent) {
            CornerButton(imageVector = Icons.Filled.Delete, contentDescription = "onCLickRight") {
                onCLickRight(images[index])
            }
        }
    }
}

@Composable
fun ImageCarousel(
        items: List<ImageSliderModel>,
        modifier: Modifier = Modifier,
        itemContent: @Composable (index: Int) -> Unit,
) {
    val pagerState = rememberPagerState { items.size }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)) {
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
