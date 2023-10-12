package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor

@Composable
fun CustomTopAppBar(navHostController: NavHostController) {
    TopAppBar(modifier = Modifier.fillMaxWidth()
            .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.topAppBarHomeBackgroundColor,
            title = {
                Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                    Text(
                            text = stringResource(R.string.app_name_top_bar),
                            fontFamily = SOLOFontName,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(HOME_ICON_HEIGHT),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.topAppBarContentColor,
                    )
                }
            },
            navigationIcon = {
                IconButton(
                        onClick = {
                            navHostController.navigate(Screen.Home.route)
                        }
                ) {
                    Icon(imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.home_icon),
                            modifier = Modifier.height(HOME_ICON_HEIGHT),
                            tint = MaterialTheme.colors.topAppBarContentColor)
                }
            }
    )
}
