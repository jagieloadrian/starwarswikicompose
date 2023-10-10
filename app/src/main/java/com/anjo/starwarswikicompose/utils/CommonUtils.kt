package com.anjo.starwarswikicompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.anjo.starwarswikicompose.utils.Constants.ASSETS_PATH

fun calculatePathToImage(category: Category, id:String):String {
    return "$ASSETS_PATH/${category.categoryName.lowercase()}/$id.jpg"
}

@Composable
fun getLocalWidth():Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@Composable
fun getLocalHeight():Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}