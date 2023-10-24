package com.anjo.starwarswikicompose.utils

enum class Category(
        val categoryName:String,
        val navMainUrl : String,
        val navArgId: String
) {
    FILMS("Films", "details_movie", "movieId"),
    PEOPLE("People","details_person", "personId"),
    PLANETS("Planets", "details_planet", "planetId"),
    SPECIES("Species", "details_specie", "specieId"),
    STARSHIPS("Starships","details_starship", "starshipId"),
    VEHICLES("Vehicles", "details_vehicle", "vehicleId")
}

fun getCategoryByMainUrl(mainUrl:String) : Category? {
    return Category.entries.associateBy { it.navMainUrl }[mainUrl]
}