package com.anjo.starwarswikicompose.tools

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category

object TestTools {

    val movies = listOf(UniversalChunkDto("movieId1", "movie1", "movieDesc1", Category.FILMS),
            UniversalChunkDto("movieId2", "movie2", "movieDesc2", Category.FILMS),
            UniversalChunkDto("movieId3", "movie3", "movieDesc3", Category.FILMS))

    val people = listOf(UniversalChunkDto("personId1", "person1", "personDesc1", Category.PEOPLE),
            UniversalChunkDto("personId2", "person2", "personDesc2", Category.PEOPLE),
            UniversalChunkDto("personId3", "person3", "personDesc3", Category.PEOPLE))

    val planet = listOf(UniversalChunkDto("planetId1", "planet1", "planetDesc1", Category.PLANETS),
            UniversalChunkDto("planetId2", "planet2", "planetDesc2", Category.PLANETS),
            UniversalChunkDto("planetId3", "planet3", "planetDesc3", Category.PLANETS))

    val specie = listOf(UniversalChunkDto("specieId1", "specie1", "specieDesc1", Category.SPECIES),
            UniversalChunkDto("specieId2", "specie2", "specieDesc2", Category.SPECIES),
            UniversalChunkDto("specieId3", "specie3", "specieDesc3", Category.SPECIES))

    val starship = listOf(UniversalChunkDto("starshipId1", "starship1", "starshipDesc1", Category.STARSHIPS),
            UniversalChunkDto("starshipId2", "starship2", "starshipDesc2", Category.STARSHIPS),
            UniversalChunkDto("starshipId3", "starship3", "starshipDesc3", Category.STARSHIPS))

    val vehicle = listOf(UniversalChunkDto("vehicleId1", "vehicle1", "vehicleDesc1", Category.VEHICLES),
            UniversalChunkDto("vehicleId2", "vehicle2", "vehicleDesc2", Category.VEHICLES),
            UniversalChunkDto("vehicleId3", "vehicle3", "vehicleDesc3", Category.VEHICLES))
}