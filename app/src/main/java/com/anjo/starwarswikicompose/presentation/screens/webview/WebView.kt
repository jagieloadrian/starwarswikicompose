package com.anjo.starwarswikicompose.presentation.screens.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.common.errorempty.ErrorScreenWrapper
import com.anjo.starwarswikicompose.services.interceptor.NetworkConnectionInterceptor
import com.anjo.starwarswikicompose.utils.Constants.ERROR_UNAVAILABLE_INTERNET
import com.anjo.starwarswikicompose.utils.Constants.WOOKIEPEDIA_URL
import com.anjo.starwarswikicompose.utils.TestTags.WEBVIEW_SCREEN_TAG
import com.anjo.starwarswikicompose.utils.TestTags.WEB_VIEW_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(navController: NavHostController) {
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()
    val onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            delay(1000)
        }
        isRefreshing = false
    }

    Scaffold(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = { CustomTopAppBar(navHostController = navController) },
        bottomBar = { CustomBottomAppBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .testTag(WEBVIEW_SCREEN_TAG),
            contentAlignment = Alignment.Center
        ) {
            if (!isRefreshing) {
                WebView(
                    modifier = Modifier
                        .pullToRefresh(isRefreshing = isRefreshing, state = pullRefreshState, onRefresh = onRefresh)
                )
            }
            Indicator(
                isRefreshing = isRefreshing, state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    url: String = WOOKIEPEDIA_URL,
) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val localContext = LocalContext.current

    if (hasInternetConnection(localContext)) {
        AndroidView(
            modifier = modifier.testTag(WEB_VIEW_TAG),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()
                        }
                    }
                    settings.javaScriptEnabled = true

                    loadUrl(url)
                    webView = this
                }
            }, update = {
                webView = it
            })
        BackHandler(enabled = backEnabled) {
            webView?.goBack()
        }
    } else {
        ErrorScreenWrapper(ERROR_UNAVAILABLE_INTERNET, modifier)
    }
}

private fun hasInternetConnection(context: Context): Boolean {
    val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
    return networkConnectionInterceptor.isInternetAvailable()
}
