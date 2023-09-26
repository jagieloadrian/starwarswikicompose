package com.anjo.starwarswikicompose.presentation.common//package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.anjo.starwarswikicompose.ui.theme.ABOUT_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.ShimmerDarkGray
import com.anjo.starwarswikicompose.ui.theme.ShimmerLightGray
import com.anjo.starwarswikicompose.ui.theme.ShimmerMediumGray

@Composable
fun ShimmerEffect() {
    LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = 2) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 500,
                            easing = FastOutLinearInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            )
    )
    ShimmerItem(alpha = alphaAnim)

}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(NAME_PLACEHOLDER_HEIGHT),
            color = if (isSystemInDarkTheme())
                Color.Black else ShimmerLightGray,
            shape = RoundedCornerShape(size = LARGE_PADDING)
    ) {
        Column(
                modifier = Modifier
                        .padding(all = MEDIUM_PADDING),
                verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                    modifier = Modifier
                            .alpha(alpha = alpha)
                            .fillMaxWidth(0.5f)
                            .height(NAME_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(size = SMALL_PADDING)
            ) { }
            Spacer(modifier = Modifier.padding(all = SMALL_PADDING))
            repeat(3) {
                Surface(
                        modifier = Modifier
                                .alpha(alpha = alpha)
                                .fillMaxWidth()
                                .height(ABOUT_PLACEHOLDER_HEIGHT),
                        color = if (isSystemInDarkTheme())
                            ShimmerDarkGray else ShimmerMediumGray,
                        shape = RoundedCornerShape(size = SMALL_PADDING)
                ) { }
                Spacer(modifier = Modifier.padding(all = EXTRA_SMALL_PADDING))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(5) {
                    Surface(
                            modifier = Modifier
                                    .alpha(alpha = alpha)
                                    .size(ABOUT_PLACEHOLDER_HEIGHT),
                            color = if (isSystemInDarkTheme())
                                ShimmerDarkGray else ShimmerMediumGray,
                            shape = RoundedCornerShape(size = SMALL_PADDING)
                    ) { }
                    Spacer(modifier = Modifier.padding(all = SMALL_PADDING))
                }
            }
        }
    }
}
