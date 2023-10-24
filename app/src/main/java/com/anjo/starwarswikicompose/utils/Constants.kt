package com.anjo.starwarswikicompose.utils

import com.anjo.starwarswikicompose.BuildConfig

object Constants {

    const val ON_BOARDING_PAGE_COUNT = 3
    const val LAST_ON_BOARDING_PAGE = 2
    const val MAX_LINES_NUMBER = 2
    const val GO_TO_APP = "Go to App"
    const val LIMIT_TEXT_IN_LINE = 20
    const val DETAILS_PERSON_ARGUMENT_KEY = "personId"
    const val DETAILS_MOVIE_ARGUMENT_KEY = "movieId"
    const val DETAILS_PLANET_ARGUMENT_KEY = "planetId"
    const val DETAILS_SPECIE_ARGUMENT_KEY = "specieId"
    const val DETAILS_STARSHIP_ARGUMENT_KEY = "starshipId"
    const val DETAILS_VEHICLE_ARGUMENT_KEY = "vehicleId"
    const val AUTO_SLIDE_DURATION = 3000L
    const val ASSETS_PATH = "file:///android_asset"
    const val APOLLO_BASE_URL = "https://swapi-graphql.eskerda.vercel.app/"
    const val FLICKR_BASE_URL = "https://www.flickr.com/"
    const val FLICKR_METHOD_SEARCH_PHOTOS = "flickr.photos.search"
    const val FLICKR_METHOD_RECENT_PHOTOS = "flickr.photos.getRecent"

    const val FLICKR_BASE_URL_IMAGE = "https://live.staticflickr.com"

    const val FLICKR_KEY = BuildConfig.FLICKR_API

    const val LESS_WHITE_BACKGROUND_COPY = 0.2f
    const val MEDIUM_WHITE_BACKGROUND_COPY = 0.8f

    const val PREFERENCES_NAME = "sw_wiki_preferences"
    const val PREFERENCES_KEY = "on_boarding-completed"

    const val IMAGE_SLIDER_TABLE = "image_slider_table"

    //TODO zaimplementować usuwanie zdjęc, customowe toasty, wyciszanie czasowo muzyki
}