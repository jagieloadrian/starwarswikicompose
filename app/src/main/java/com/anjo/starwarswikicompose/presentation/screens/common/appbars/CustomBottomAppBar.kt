package com.anjo.starwarswikicompose.presentation.screens.common.appbars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.mainContentColor

@Composable
fun CustomBottomAppBar(navHostController: NavHostController) {
    BottomAppBar(
            modifier = Modifier.fillMaxWidth()
                    .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.mainBackgroundColors,
    ) {
        Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
        ) {
            BottomTab({ navHostController.navigate(Screen.Home.route) },
                    painterResource(R.drawable.dictionary_icon),
                    stringResource(R.string.wiki_bottom_bar))
            BottomTab({ navHostController.navigate(Screen.ImageSearch.route) },
                    painterResource(R.drawable.image_icon),
                    stringResource(R.string.images_bottom_bar))
            BottomTab({ navHostController.navigate(Screen.WookiepediaWebView.route) },
                    painterResource(R.drawable.baseline_travel_explore_24),
                    stringResource(R.string.more_bottom_bar))
        }
    }
}

@Composable
private fun BottomTab(
        function: () -> Unit,
        painterResource: Painter,
        stringResource: String,
) {
    Row(modifier = Modifier
            .clickable { function() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
                painter = painterResource,
                contentDescription = stringResource(R.string.bottom_tab_description),
                modifier = Modifier.height(HOME_ICON_HEIGHT)
                        .align(Alignment.CenterVertically),
                tint = MaterialTheme.colors.mainContentColor,
        )
        Text(text = stringResource,
                modifier = Modifier.padding(SMALL_PADDING),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName,
                color = White)
    }
}