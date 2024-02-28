package com.anjo.starwarswikicompose.presentation.screens.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.presentation.common.ErrorScreenWrapper
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.utils.Constants.ERROR_UNAVAILABLE_INTERNET
import com.anjo.starwarswikicompose.utils.Constants.WOOKIEPEDIA_URL
import com.anjo.starwarswikicompose.utils.hasInternetConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WebViewScreen(navController: NavHostController) {
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            delay(1500)
            isRefreshing = false
        }
    })

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center) {
            if (!isRefreshing) {
                WebView(modifier = Modifier
                        .pullRefresh(pullRefreshState))
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
        modifier: Modifier = Modifier,
) {
    val url = WOOKIEPEDIA_URL
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val localContext = LocalContext.current

    if (hasInternetConnection(localContext)) {
        AndroidView(
                modifier = modifier,
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
