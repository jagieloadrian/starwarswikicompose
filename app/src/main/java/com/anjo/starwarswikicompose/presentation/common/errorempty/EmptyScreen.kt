package com.anjo.starwarswikicompose.presentation.common.errorempty

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun EmptyScreen(
        category: Category?,
        text: String = "",
) {
    val textFiller = category?.categoryName ?: text

    val message by remember {
        mutableStateOf("Find your favorite ${textFiller.lowercase()}!")
    }
    val icon by remember {
        mutableIntStateOf(R.drawable.ic_search_document)
    }

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) 0.5f else 1f,
            animationSpec = tween(
                    durationMillis = 2500
            ), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    EmptyContent(
            alphaAnim = alphaAnim,
            icon = icon,
            message = message
    )
}

@Composable
private fun EmptyContent(
        alphaAnim: Float,
        icon: Int,
        message: String,
) {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Icon(
                modifier = Modifier
                        .size(NETWORK_ERROR_ICON_HEIGHT)
                        .alpha(alpha = alphaAnim),
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error_icon),
                tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
        )
        Text(
                modifier = Modifier
                        .padding(SMALL_PADDING)
                        .alpha(alpha = alphaAnim),
                text = message,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
    }
}
