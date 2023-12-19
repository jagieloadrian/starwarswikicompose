package com.anjo.starwarswikicompose.utils

import com.anjo.starwarswikicompose.BuildConfig

object Constants {

    const val ON_BOARDING_PAGE_COUNT = 3
    const val LAST_ON_BOARDING_PAGE = 2
    const val MAX_LINES_NUMBER = 2
    const val GO_TO_APP = "Ok, I'm going to app!"
    const val LIMIT_TEXT_IN_LINE = 20
    const val DETAILS_PERSON_ARGUMENT_KEY = "personId"
    const val DETAILS_MOVIE_ARGUMENT_KEY = "movieId"
    const val DETAILS_PLANET_ARGUMENT_KEY = "planetId"
    const val DETAILS_SPECIE_ARGUMENT_KEY = "specieId"
    const val DETAILS_STARSHIP_ARGUMENT_KEY = "starshipId"
    const val DETAILS_VEHICLE_ARGUMENT_KEY = "vehicleId"
    const val ASSETS_PATH = "file:///android_asset"
    const val WOOKIEPEDIA_URL = "https://starwars.fandom.com/wiki/Main_Page"
    const val APOLLO_BASE_URL = "https://swapi-graphql.eskerda.vercel.app/"
    const val FLICKR_BASE_URL = "https://www.flickr.com/"
    const val FLICKR_METHOD_SEARCH_PHOTOS = "flickr.photos.search"
    const val FLICKR_METHOD_RECENT_PHOTOS = "flickr.photos.getRecent"

    const val FLICKR_BASE_URL_IMAGE = "https://live.staticflickr.com"
    const val FLICKR_EXT = ".jpg"

    const val FLICKR_KEY = BuildConfig.FLICKR_API
    const val FEEDBACK_RECEIVER = BuildConfig.FEEDBACK_RECEIVER

    const val LESS_WHITE_BACKGROUND_COPY = 0.2f
    const val MEDIUM_WHITE_BACKGROUND_COPY = 0.8f

    const val PREFERENCES_NAME = "sw_wiki_preferences"
    const val PREFERENCES_KEY = "on_boarding-completed"

    const val IMAGE_SLIDER_TABLE = "image_slider_table"
    const val NOTES_TABLE = "notes_table"

    const val DEFAULT_VALUE = "Default Value"

    const val SAVE_IN_PREFIX = "Save in "
    const val IMAGE_NOT_FOUND = "Image not found, go to searcher!"
    const val REFRESH_IMAGES = "Refresh images"
    const val DELETE_AND_REFRESH_IMAGES = "Delete image and refresh images"
    const val COPIED_TO_CLIPBOARD = "Copied to clipboard"
    const val ASKING_FOR_USER = "Where are you?"
    const val DESCRIPTION_ASKING_FOR_USER = "We missed you... Come look for new things \uD83D\uDE80"


    const val NOTIFICATION_NAME = "Main Channel"
    const val NOTIFICATION_CHANNEL = "main_channel_Id"
    const val NOTIFICATION_WORK_TAG = "main_notification_work"
}