package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor

@Composable
fun HomeTopBar() {
    TopAppBar(modifier = Modifier.fillMaxWidth()
            .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.topAppBarHomeBackgroundColor,
            title = {
                Text(
                        text = stringResource(R.string.app_name_top_bar),
                        fontFamily = SOLOFontName,
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(HOME_ICON_HEIGHT),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.topAppBarContentColor,
                )
            }
    )
}
