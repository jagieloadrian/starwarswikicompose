package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

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
        Icon(
                modifier = Modifier.size(PICTURE_HEIGHT)
                        .alpha(alpha = alphaAnim),
                painter = painterResource(R.drawable.meme_this_is_fine_dog),
                contentDescription = stringResource(R.string.network_error_icon)
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