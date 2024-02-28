package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun ErrorScreenWrapper(text: String, modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds),
            contentAlignment = Alignment.Center) {
        ErrorScreen(text)
    }
}

@Composable
fun ErrorScreen(
        text: String,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 2500,
                            easing = FastOutLinearInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            ), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    ErrorContent(alphaAnim, text)
}

@Composable
fun ErrorContent(
        alphaAnim: Float,
        message: String,
) {
    Column(
            modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.meme_this_is_fine_dog),
                contentDescription = stringResource(R.string.network_error_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                        .height(PICTURE_HEIGHT)
                        .alpha(alphaAnim)
        )
        Text(
                modifier = Modifier.padding(SMALL_PADDING)
                        .alpha(alpha = alphaAnim),
                text = message,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
    }
}