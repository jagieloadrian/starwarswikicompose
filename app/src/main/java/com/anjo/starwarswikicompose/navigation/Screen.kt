package com.anjo.starwarswikicompose.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome_screen")
    data object Home : Screen("home_screen")
    data object PersonDetail : Screen("details_person/{personId}") {
        fun passPersonId(personId: String): String {
            return "details_person/$personId"
        }
    }

    data object MovieDetail : Screen("details_movie/{movieId}") {
        fun passMovieId(movieId: String): String {
            return "details_movie/$movieId"
        }
    }

    data object PlanetDetail : Screen("details_planet/{planetId}") {
        fun passPlanetId(planetId: String): String {
            return "details_planet/$planetId"
        }
    }

    data object SpecieDetail : Screen("details_specie/{specieId}") {
        fun passSpecieId(specieId: String): String {
            return "details_specie/$specieId"
        }
    }

    data object StarshipDetail : Screen("details_starship/{starshipId}") {
        fun passStarshipId(starshipId: String): String {
            return "details_starship/$starshipId"
        }
    }

    data object VehicleDetail : Screen("details_vehicle/{vehicleId}") {
        fun passVehicleId(vehicleId: String): String {
            return "details_vehicle/$vehicleId"
        }
    }

    data object ImageSearch : Screen("image_search_screen")
    data object WookiepediaWebView : Screen("wookiepedia_webview_screen")
}