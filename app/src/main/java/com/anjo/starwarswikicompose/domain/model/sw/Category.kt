package com.anjo.starwarswikicompose.domain.model.sw

import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES

enum class Category(val categoryName: String) {
    ALL("All"),
    FILMS("Films"),
    PEOPLE("People"),
    PLANETS("Planets"),
    SPECIES("Species"),
    STARSHIPS("Starships"),
    VEHICLES("Vehicles")
}

val CategoryWithoutAllProperty = listOf(FILMS, PEOPLE, PLANETS, SPECIES, STARSHIPS, VEHICLES)
