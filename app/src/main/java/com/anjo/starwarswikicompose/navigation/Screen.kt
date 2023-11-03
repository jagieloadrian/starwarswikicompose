package com.anjo.starwarswikicompose.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object PersonDetail:Screen("details_person/{personId}") {
        fun passPersonId(personId:String):String {
            return "details_person/$personId"
        }
    }
    object MovieDetail: Screen("details_movie/{movieId}") {
        fun passMovieId(movieId:String):String {
            return "details_movie/$movieId"
        }
    }
    object PlanetDetail: Screen("details_planet/{planetId}") {
        fun passPlanetId(planetId:String):String {
            return "details_planet/$planetId"
        }
    }
    object SpecieDetail: Screen("details_specie/{specieId}") {
        fun passSpecieId(specieId:String):String {
            return "details_specie/$specieId"
        }
    }
    object StarshipDetail: Screen("details_starship/{starshipId}") {
        fun passStarshipId(starshipId:String):String {
            return "details_starship/$starshipId"
        }
    }
    object VehicleDetail: Screen("details_vehicle/{vehicleId}") {
        fun passVehicleId(vehicleId:String):String {
            return "details_vehicle/$vehicleId"
        }
    }
    object ImageSearch:Screen("image_search_screen")
    object WookiepediaWebView:Screen("wookiepedia_webview_screen")
}