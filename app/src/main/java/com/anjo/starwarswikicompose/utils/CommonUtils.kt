package com.anjo.starwarswikicompose.utils

import androidx.compose.ui.platform.Clipboard
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL_IMAGE
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_EXT
import com.anjo.starwarswikicompose.utils.Constants.NA
import com.anjo.starwarswikicompose.utils.Constants.UNKNOWN
import kotlinx.coroutines.flow.StateFlow

fun isFromLocalStorage(sourceType: SourceType): Boolean {
    return when (sourceType) {
        SourceType.APOLLO -> false
        SourceType.ROOM   -> true
    }
}

fun getSourceType(isFromLocalStore: Boolean): SourceType {
    return when (isFromLocalStore) {
        true  -> SourceType.ROOM
        false -> SourceType.APOLLO
    }
}

fun getDescriptionName(name: String?, unitName: UnitName?): String {
    return if (shouldBeEmojiDescription(name)) EMOJI else if (unitName != null) {
        "$name ${unitName.description}"
    } else name.orEmpty()
}

private fun shouldBeEmojiDescription(name: String?): Boolean {
    return name.isNullOrBlank() || listOf(UNKNOWN, NA).contains(name)
}

suspend fun updateImages(images: StateFlow<List<ImageSliderModel>>, updatePhotos: (String) -> Unit) {
    images.collect { images ->
        images.forEach { image ->
            updatePhotos(image.url)
        }
    }
}

suspend fun addImageFunction(
        clipManager: Clipboard,
        navController: NavHostController,
        runSaving: (String) -> Unit,
        navControllerSnackBar: suspend () -> Unit,
) {
    val photoUrl = clipManager.getClipEntry()?.clipData?.getItemAt(0)?.text?.toString()
    photoUrl?.let {
        if (validateUrl(photoUrl)) {
            runSaving(photoUrl)
        } else {
            navController.navigate(Screen.ImageSearch.route)
            navControllerSnackBar()
        }
    }
}

private fun validateUrl(photoUrl: String?): Boolean {
    if (photoUrl != null) {
        return photoUrl.startsWith(FLICKR_BASE_URL_IMAGE, true) && photoUrl.endsWith(FLICKR_EXT, true)
    }
    return false
}
