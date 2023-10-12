package com.anjo.starwarswikicompose.presentation.screens.images

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ImageScreen(
        imageViewModel: ImageViewModel = hiltViewModel()
) {

    val searchQuery by imageViewModel.searchQuery
    val photoResponse by imageViewModel.fetchedPhotoInfos.collectAsState()

    Log.e("ImageScreen", photoResponse.toString())

    Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
//            .paint(painter = painterResource(R.drawable.stars_image))
    ) {
        Text(text = photoResponse.photos?.photo.toString())
    }
}