package com.anjo.starwarswikicompose.presentation.common.ads

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants.BANNER_NOT_FOUND_TEXT
import com.anjo.starwarswikicompose.utils.Constants.BANNER_UNIT_ID
import com.anjo.starwarswikicompose.utils.TestTags.BANNER_BOX_TAG
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError


@Composable
fun BannerAdMobile(
    modifier: Modifier = Modifier,
    adUnitId: String = BANNER_UNIT_ID,
    onClickAction: () -> Unit
) {
    val bannerHeight = 52.dp

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(bannerHeight)
            .clip(MaterialTheme.shapes.medium)
            .border(SMALL_BORDER, MaterialTheme.colorScheme.secondary)

    ) {
        BannerAdView(adUnitId = adUnitId) { onClickAction() }
    }

}

@Composable
fun BannerAdView(adUnitId: String,
                 onClickAction: () -> Unit) {
    val context = LocalContext.current
    var adLoaded by remember { mutableStateOf(false) }

    val adView = AdView(context).apply {
        setAdSize(AdSize.BANNER)
        this.adUnitId = adUnitId

        adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.i("BANNERMOBILE", "Ad loaded successfully")
                adLoaded = true
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e("BANNERMOBILE", "Ad failed: ${error.code} - ${error.message}")
                adLoaded = false
            }
        }

        loadAd(AdRequest.Builder().build())
    }

        Box(
            Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { if (!adLoaded) onClickAction() }
                .testTag(BANNER_BOX_TAG),
            contentAlignment = Alignment.Center,
        ) {
            if (adLoaded) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clip(MaterialTheme.shapes.medium),
                    factory = {
                        adView
                    }
                )
            } else {
                Text(
                    BANNER_NOT_FOUND_TEXT,
                    fontFamily = SOLOFontName,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }

}