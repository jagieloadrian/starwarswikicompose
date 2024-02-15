package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SHIMMER_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.ShimmerDarkGray
import com.anjo.starwarswikicompose.ui.theme.ShimmerMediumGray
import com.anjo.starwarswikicompose.utils.Constants.SHIMMER_EFFECT_TAG

@Composable
fun ShimmerEffect() {
    LazyColumn(
            modifier = Modifier.testTag(SHIMMER_EFFECT_TAG),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = 3) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
private fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 1000,
                            easing = FastOutLinearInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            ), label = ""
    )
    ShimmerItem(alpha = alphaAnim)
}

@Composable
private fun ShimmerItem(alpha: Float) {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha)
                    .height(NAME_PLACEHOLDER_HEIGHT)
                    .clip(CircleShape)
                    .background(brush = Brush.linearGradient(SHIMMER_COLORS))
    ) {
        Column(
                modifier = Modifier
                        .padding(all = MEDIUM_PADDING),
                verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                    modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(NAME_PLACEHOLDER_HEIGHT)
                            .alpha(0.4f),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(size = SMALL_PADDING)
            ) { }
        }
    }
}