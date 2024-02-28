package com.anjo.starwarswikicompose.utils

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.ERROR
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.services.interceptor.NetworkConnectionInterceptor
import com.anjo.starwarswikicompose.utils.Constants.ASSETS_PATH
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL_IMAGE
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_EXT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun calculatePathToImage(category: Category, id: String): String {
    return "$ASSETS_PATH/${category.categoryName.lowercase()}/$id.jpg"
}

@Composable
fun getLocalWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@Composable
fun getLocalHeight(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}

fun buildImageUrl(photo: FlickrPhoto): String {
    return "$FLICKR_BASE_URL_IMAGE/${photo.server}/${photo.id}_${photo.secret}$FLICKR_EXT"
}

fun validateUrl(photoUrl: String?): Boolean {
    if (photoUrl != null) {
        return photoUrl.startsWith(FLICKR_BASE_URL_IMAGE, true) && photoUrl.endsWith(FLICKR_EXT, true)
    }
    return false
}

fun addImageFunction(
        clipManager: ClipboardManager,
        navController: NavHostController,
        runSaving: (String) -> Unit,
        runSavingSnackBar: () -> Unit,
        navControllerSnackBar: () -> Unit,
) {
    val photoUrl = clipManager.getText()?.text
    photoUrl?.let {
        if (validateUrl(photoUrl)) {
            runSaving(photoUrl)
            runSavingSnackBar()
        } else {
            navController.navigate(Screen.ImageSearch.route)
            navControllerSnackBar()
        }
    }
}

fun UniversalChunk.toImageSliderModel(photoUrl: String, category: Category): ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = category
    )
}

fun navigateToProperlyCompose(navController: NavHostController, itemId: String, category: Category) {
    when (category) {
        FILMS     -> {
            navController.navigate(Screen.MovieDetail.passMovieId(itemId))
            return
        }

        PEOPLE    -> {
            navController.navigate(Screen.PersonDetail.passPersonId(itemId))
            return
        }

        PLANETS   -> {
            navController.navigate(Screen.PlanetDetail.passPlanetId(itemId))
            return
        }

        SPECIES   -> {
            navController.navigate(Screen.SpecieDetail.passSpecieId(itemId))
            return
        }

        STARSHIPS -> {
            navController.navigate(Screen.StarshipDetail.passStarshipId(itemId))
            return
        }

        VEHICLES  -> {
            navController.navigate(Screen.VehicleDetail.passVehicleId(itemId))
            return
        }
    }
}

fun volumeUpMusic(scope: CoroutineScope, audioManager: AudioManager, maxVol: Int) {
    scope.launch {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
            var currentVol = 0
            while (currentVol != (maxVol + 1)) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0)
                currentVol += 1
                delay(100)
            }
        }
    }
}

fun muteMusic(scope: CoroutineScope, audioManager: AudioManager) {
    scope.launch {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0) {
            var currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            while (currentVol != -1) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0)
                currentVol -= 1
                delay(100)
            }
        }
    }
}

fun emptyConnection(): Connection = Connection(0, listOf())

fun hasInternetConnection(context: Context): Boolean {
    val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
    return networkConnectionInterceptor.isInternetAvailable()
}

fun DetailObjectState.isSuccess(): Boolean {
    return this == SUCCESS
}

fun DetailObjectState.isLoading(): Boolean {
    return this == LOADING
}

fun DetailObjectState.isError(): Boolean {
    return this == ERROR
}