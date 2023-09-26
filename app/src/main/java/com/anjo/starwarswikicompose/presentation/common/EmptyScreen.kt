package com.anjo.starwarswikicompose.presentation.common

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
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.ConnectException
import java.net.SocketTimeoutException


fun calculatePagingResult(
        dataClass: Any?,
        totalCount: Int = 0
): Boolean {
    dataClass.apply {
        return when {
            dataClass != null -> {
                false
            }

            totalCount < 1 -> {
                false
            }

            else -> {
                true
            }
        }
    }
}

@Composable
fun ShowEmptyScreen(shouldExposeEmptyScreen: Boolean) {
    return when (shouldExposeEmptyScreen) {
        true -> EmptyScreen()
        false -> return
    }
}

@Composable
fun EmptyScreen(
        error: LoadState.Error? = null,
) {
    var message by remember {
        mutableStateOf("Find your favorite Hero!")
    }
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_search_document)
    }

    if (error != null) {
        message = parseErrorMessage(error = error)
        icon = R.drawable.ic_network_error
    }

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
            animationSpec = tween(
                    durationMillis = 1000
            )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
            alphaAnim = alphaAnim,
            icon = icon,
            message = message,
            heroes = null,
            error = error
    )
}

@Composable
fun EmptyContent(
        alphaAnim: Float,
        icon: Int,
        message: String,
        heroes: LazyPagingItems<Any>? = null,
        error: LoadState.Error? = null
) {
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    SwipeRefresh(
            swipeEnabled = error != null,
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                heroes?.refresh()
                isRefreshing = false
            }
    ) {
        Column(
                modifier = Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Icon(
                    modifier = Modifier.size(NETWORK_ERROR_ICON_HEIGHT)
                            .alpha(alpha = alphaAnim),
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(R.string.network_error_icon),
                    tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
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
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Unavailable"
        }

        is ConnectException -> {
            "Internet Unavailable"
        }

        else -> {
            "Unknown Error"
        }
    }
}
