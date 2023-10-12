package com.anjo.starwarswikicompose.presentation.screens.common

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
import com.anjo.starwarswikicompose.ui.theme.customTabTextColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor

@Composable
fun CustomBottomAppBar(navHostController: NavHostController) {
    BottomAppBar(
            modifier = Modifier.fillMaxWidth()
                    .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.topAppBarHomeBackgroundColor,
    ) {
        Row (
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
        ){
            Row(modifier = Modifier
                    .clickable { navHostController.navigate(Screen.Home.route) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                        painter = painterResource(R.drawable.dictionary_icon),
                        contentDescription = stringResource(R.string.dictionary_icon),
                        modifier = Modifier.height(HOME_ICON_HEIGHT)
                                .align(Alignment.CenterVertically),
                        tint = MaterialTheme.colors.topAppBarContentColor,
                )
                Text(text = stringResource(R.string.wiki_bottom_bar),
                        modifier = Modifier.padding(SMALL_PADDING),
                        textAlign = TextAlign.Center,
                        fontFamily = SOLOFontName,
                        color = MaterialTheme.colors.customTabTextColor)
            }
            Row(
                    modifier = Modifier.clickable { navHostController.navigate(Screen.ImageSearch.route) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.image_icon),
                        contentDescription = stringResource(R.string.image_icon),
                        modifier = Modifier.height(HOME_ICON_HEIGHT)
                                .align(Alignment.CenterVertically),
                        tint = MaterialTheme.colors.topAppBarContentColor)
                Text(text = stringResource(R.string.images_bottom_bar),
                        modifier = Modifier.padding(SMALL_PADDING),
                        textAlign = TextAlign.Center,
                        fontFamily = SOLOFontName,
                        color = MaterialTheme.colors.customTabTextColor)
            }
        }
    }
}