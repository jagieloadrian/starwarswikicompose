package com.anjo.starwarswikicompose.presentation.common.loading

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.animatedBorder
import com.anjo.starwarswikicompose.ui.theme.SHIMMER_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.STORMTROOPER_DRAW

@Composable
fun LoadingScreen() {
    val angleOffSet = 45f
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotateAnimation by infiniteTransition.animateFloat(
            initialValue = -angleOffSet,
            targetValue = angleOffSet,
            animationSpec = infiniteRepeatable(
                    animation = tween(1000, 0, EaseOutCubic),
                    repeatMode = RepeatMode.Reverse), label = ""
    )

    Box(modifier = Modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds),
            contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
                .animatedBorder(borderColors = SHIMMER_COLORS,
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        borderWidth = SMALL_PADDING_FOR_INFOBOX)) {
            Image(painter = painterResource(R.drawable.mini_stormtrooper),
                    contentDescription = STORMTROOPER_DRAW,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                            .size(VEHICLE_PICTURE_HEIGHT)
                            .graphicsLayer {
                                rotationY = rotateAnimation
                            }
            )
        }
    }
}