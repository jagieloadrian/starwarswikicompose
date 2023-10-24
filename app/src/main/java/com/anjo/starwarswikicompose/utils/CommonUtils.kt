package com.anjo.starwarswikicompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.anjo.GetFilmQuery
import com.anjo.GetPersonQuery
import com.anjo.GetPlanetQuery
import com.anjo.GetSpecieQuery
import com.anjo.GetStarshipQuery
import com.anjo.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.utils.Category.FILMS
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.PLANETS
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.Category.VEHICLES
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

fun buildImageUrl(photo: FlickrPhoto): String {
    return "$FLICKR_BASE_URL_IMAGE/${photo.server}/${photo.id}_${photo.secret}.jpg"
}

fun GetFilmQuery.Film.toImageSliderModel(photoUrl:String): ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = FILMS
    )
}

fun GetPersonQuery.Person.toImageSliderModel(photoUrl: String):ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = PEOPLE
    )
}

fun GetPlanetQuery.Planet.toImageSliderModel(photoUrl: String):ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = PLANETS
    )
}

fun GetSpecieQuery.Species.toImageSliderModel(photoUrl: String):ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = SPECIES
    )
}

fun GetStarshipQuery.Starship.toImageSliderModel(photoUrl: String):ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = STARSHIPS
    )
}

fun GetVehicleQuery.Vehicle.toImageSliderModel(photoUrl: String):ImageSliderModel {
    return ImageSliderModel(
            objectId = id,
            url = photoUrl,
            objectType = VEHICLES
    )
}