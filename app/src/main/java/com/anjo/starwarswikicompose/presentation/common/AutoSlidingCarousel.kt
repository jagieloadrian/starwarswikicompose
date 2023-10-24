package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.AUTO_SLIDE_DURATION
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GallerySlider(images: List<ImageSliderModel>) {
    val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
    ) {
        images.size
    }
    Card(modifier = Modifier.padding(SMALL_PADDING),
            shape = RoundedCornerShape(MEDIUM_PADDING)) {
        AutoSlidingCarousel(
                pagerState = pagerState,
                itemsCount = images.size,
        ) { index ->
            AsyncImage(
                    model = images[index].url,
                    placeholder = painterResource(R.drawable.image_icon),
                    error = painterResource(R.drawable.ic_network_error),
                    contentDescription = stringResource(R.string.flickr_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(PICTURE_HEIGHT)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingCarousel(
        modifier: Modifier = Modifier,
        autoSlideDuration: Long = AUTO_SLIDE_DURATION,
        itemsCount: Int,
        pagerState: PagerState = rememberPagerState(pageCount = { itemsCount }),
        itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(autoSlideDuration)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(state = pagerState) { page ->
            itemContent(page)
        }

        Surface(
                modifier = Modifier
                        .padding(EXTRA_SMALL_PADDING)
                        .align(Alignment.BottomCenter),
                shape = CircleShape,
                color = Color.Black.copy(0.5f)
        ) {
            DotsIndicator(
                    totalDots = itemsCount,
                    selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage)
        }
    }
}