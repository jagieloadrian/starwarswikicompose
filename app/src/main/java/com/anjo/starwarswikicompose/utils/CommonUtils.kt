package com.anjo.starwarswikicompose.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.anjo.starwarswikicompose.domain.model.FlickrPhoto
import com.anjo.starwarswikicompose.utils.Constants.ASSETS_PATH
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL_IMAGE

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

fun buildImageUrl(photo: FlickrPhoto):String {
    val url ="$FLICKR_BASE_URL_IMAGE/${photo.server}/${photo.id}_${photo.secret}.jpg"
    Log.e("URL", "URL of photo: $url")
    return url
}