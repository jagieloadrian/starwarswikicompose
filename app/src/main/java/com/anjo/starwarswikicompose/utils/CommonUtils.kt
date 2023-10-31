package com.anjo.starwarswikicompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.GetFilmQuery
import com.anjo.GetPersonQuery
import com.anjo.GetPlanetQuery
import com.anjo.GetSpecieQuery
import com.anjo.GetStarshipQuery
import com.anjo.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.utils.Category.FILMS
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.PLANETS
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.Category.VEHICLES
import com.anjo.starwarswikicompose.utils.Constants.ASSETS_PATH
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL_IMAGE
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_EXT

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
        runSaving:(String)-> Unit,
        runSavingSnackBar:()->Unit,
        navControllerSnackBar:()->Unit
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

fun <T> navigateToProperlyCompose(navController: NavHostController, item: T, category: Category) {
    when (category) {
        FILMS     -> {
            val currentItem = item as GetAllFilmsQuery.Film
            navController.navigate(Screen.MovieDetail.passMovieId(currentItem.id))
            return
        }

        PEOPLE    -> {
            val currentItem = item as GetAllPeoplesQuery.Person
            navController.navigate(Screen.PersonDetail.passPersonId(currentItem.id))
            return
        }

        PLANETS   -> {
            val currentItem = item as GetAllPlanetsQuery.Planet
            navController.navigate(Screen.PlanetDetail.passPlanetId(currentItem.id))
            return
        }

        SPECIES   -> {
            val currentItem = item as GetAllSpeciesQuery.Species
            navController.navigate(Screen.SpecieDetail.passSpecieId(currentItem.id))
            return
        }

        STARSHIPS -> {
            val currentItem = item as GetAllStarshipsQuery.Starship
            navController.navigate(Screen.StarshipDetail.passStarshipId(currentItem.id))
            return
        }

        VEHICLES  -> {
            val currentItem = item as GetAllVehiclesQuery.Vehicle
            navController.navigate(Screen.VehicleDetail.passVehicleId(currentItem.id))
            return
        }
    }
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