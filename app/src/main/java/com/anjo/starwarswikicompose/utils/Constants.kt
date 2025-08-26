package com.anjo.starwarswikicompose.utils

import com.anjo.starwarswikicompose.BuildConfig

object Constants {
    const val ON_BOARDING_PAGE_COUNT = 3
    const val LAST_ON_BOARDING_PAGE = 2
    const val MAX_LINES_NUMBER = 2
    const val GO_TO_APP = "Ok, I'm going to app!"
    const val DETAILS_PERSON_ARGUMENT_KEY = "personId"
    const val DETAILS_MOVIE_ARGUMENT_KEY = "movieId"
    const val DETAILS_PLANET_ARGUMENT_KEY = "planetId"
    const val DETAILS_SPECIE_ARGUMENT_KEY = "specieId"
    const val DETAILS_STARSHIP_ARGUMENT_KEY = "starshipId"
    const val DETAILS_VEHICLE_ARGUMENT_KEY = "vehicleId"
    const val SOURCE_TYPE_ARGUMENT_KEY = "source"
    const val ASSETS_PATH = "file:///android_asset"
    const val WOOKIEPEDIA_URL = "https://starwars.fandom.com/wiki/Main_Page"
    const val APOLLO_BASE_URL = "https://swapi-graphql.eskerda.vercel.app/"
    const val FLICKR_BASE_URL = "https://www.flickr.com/"
    const val FLICKR_METHOD_SEARCH_PHOTOS = "flickr.photos.search"
    const val FLICKR_BASE_URL_IMAGE = "https://live.staticflickr.com"
    const val FLICKR_EXT = ".jpg"

    const val FLICKR_KEY = BuildConfig.FLICKR_API
    const val FEEDBACK_RECEIVER = BuildConfig.FEEDBACK_RECEIVER
    const val LESS_WHITE_BACKGROUND_COPY = 0.2f
    const val MEDIUM_WHITE_BACKGROUND_COPY = 0.8f
    const val PREFERENCES_NAME = "sw_wiki_preferences"
    const val PREFERENCES_KEY = "on_boarding_completed"
    const val NOTIFICATIONS_KEY = "notifications_enabled"
    const val ERROR_UNAVAILABLE_INTERNET = "Internet Unavailable"
    const val ERROR_UNAVAILABLE_EXT_SERVER = "External Server Unavailable"

    const val HEROES_NAME = "Heroes"
    const val PLANETS_NAME = "Planets"
    const val STARSHIPS_NAME = "Starships"
    const val VEHICLES_NAME = "Vehicles"
    const val SPECIES_NAME = "Species"
    const val MOVIES_NAME = "Movies"
    const val PHOTO_NAME = "Photos"

    const val ADD_NEW_HERO = "Add new hero"
    const val ADD_NEW_MOVIE = "Add new movie"
    const val ADD_NEW_PLANET = "Add new planet"
    const val ADD_NEW_STARSHIP = "Add new starship"
    const val ADD_NEW_VEHICLE = "Add new vehicle"
    const val ADD_NEW_SPECIE = "Add new specie"

    const val IMAGE_SLIDER_TABLE = "image_slider_table"
    const val SW_MODEL_DB = "sw_model_db"
    const val NOTES_TABLE = "notes_table"
    const val MOVIE_TABLE = "movie_table"
    const val PERSON_TABLE = "person_table"
    const val SPECIE_TABLE = "specie_table"
    const val STARSHIP_TABLE = "starship_table"
    const val VEHICLE_TABLE = "vehicle_table"
    const val PLANET_TABLE = "planet_table"
    const val UNIVERSAL_CHUNK_TABLE = "universal_chunk_table"
    const val MODEL_CHUNK_CROSS_REF_TABLE = "model_chunk_cross_ref_table"

    const val IMAGE_NOT_FOUND = "Image not found, go to searcher!"
    const val REFRESH_IMAGES = "Refresh images"
    const val DELETE_AND_REFRESH_IMAGES = "Delete image and refresh images"
    const val COPIED_TO_CLIPBOARD = "Copied to clipboard"
    const val ASKING_FOR_USER = "Where are you?"
    const val DESCRIPTION_ASKING_FOR_USER = "We missed you... Come look for new things \uD83D\uDE80"
    const val ERROR_DESCRIPTION = "Ooops! Something went wrong!"

    const val UNKNOWN = "unknown"
    const val NA = "n/a"
    const val EMOJI = "\uD83D\uDE4A"

    const val SHARE_ADDITIONAL_MESSAGE = "Wow, look at this awesome image from Star Wars Wiki!"
    const val AUTHORITY_INTENT = "com.anjo.starwarswikicompose.fileprovider"
    const val INTENT_SHARE_TITLE = "Choose an app"
    const val CACHE_NAME = "images"
    const val TEMP_FILE_NAME = "image.jpg"

    const val NOTIFICATION_NAME = "Main Channel"
    const val NOTIFICATION_CHANNEL = "main_channel_Id"
    const val NOTIFICATION_WORK_TAG = "main_notification_work"
    const val APOLLO_DB = "apollo.db"
    const val CLIPBOARD_URI_KEY = "image_url_clipboard"

    const val ANIMATED_BORDER_DURATION = 5_000

    val RELEASE_DATE_PATTERN = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}")
}