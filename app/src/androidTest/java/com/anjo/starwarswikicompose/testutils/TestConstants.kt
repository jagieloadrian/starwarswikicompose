package com.anjo.starwarswikicompose.testutils

import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES

object TestConstants {
    const val ONBOARD_IMAGE_DESCRIPTION = "On boarding Image"

    val movies = listOf(UniversalChunkDto("movieId1", "movie1", "movieDesc1", FILMS),
            UniversalChunkDto("movieId2", "movie2", "movieDesc2", FILMS),
            UniversalChunkDto("movieId3", "movie3", "movieDesc3", FILMS))

    val people = listOf(UniversalChunkDto("personId1", "person1", "personDesc1", Category.PEOPLE),
            UniversalChunkDto("personId2", "person2", "personDesc2", Category.PEOPLE),
            UniversalChunkDto("personId3", "person3", "personDesc3", Category.PEOPLE))

    val planet = listOf(UniversalChunkDto("planetId1", "planet1", "planetDesc1", PLANETS),
            UniversalChunkDto("planetId2", "planet2", "planetDesc2", PLANETS),
            UniversalChunkDto("planetId3", "planet3", "planetDesc3", PLANETS))

    val specie = listOf(UniversalChunkDto("specieId1", "specie1", "specieDesc1", SPECIES),
            UniversalChunkDto("specieId2", "specie2", "specieDesc2", SPECIES),
            UniversalChunkDto("specieId3", "specie3", "specieDesc3", SPECIES))

    val starship = listOf(UniversalChunkDto("starshipId1", "starship1", "starshipDesc1", STARSHIPS),
            UniversalChunkDto("starshipId2", "starship2", "starshipDesc2", STARSHIPS),
            UniversalChunkDto("starshipId3", "starship3", "starshipDesc3", STARSHIPS))

    val vehicle = listOf(UniversalChunkDto("vehicleId1", "vehicle1", "vehicleDesc1", VEHICLES),
            UniversalChunkDto("vehicleId2", "vehicle2", "vehicleDesc2", VEHICLES),
            UniversalChunkDto("vehicleId3", "vehicle3", "vehicleDesc3", VEHICLES))

    val movieDto =
        MovieDto(id = "", title = "movieId", episodeId = "nextEpisode",
                openingCrawl = "Opening crawl and a long text",
                producers = listOf("producer1", "producer2"), director = "directorName", releaseDate = "2000-01-01",
                characterConnection = ConnectionDto(2, listOf(people.component1(), people.component2())),
                planetConnection = ConnectionDto(2, listOf(planet.component1(), planet.component2())),
                starshipConnection = ConnectionDto(2, listOf(starship.component1(), starship.component2())),
                vehicleConnection = ConnectionDto(2, listOf(vehicle.component1(), vehicle.component2())),
                specieConnection = ConnectionDto(2, listOf(specie.component1(), specie.component2())),
                true)

    val personDto = PersonDto(
            id = "", name = "person", birthYear = "1", height = "height", mass = "it was",
            gender = "gender",
            hair = "hairs", skin = "skins", homeworld = planet.component1(), specie = specie.component1(),
            vehicleConnection = ConnectionDto(2, listOf(vehicle.component1(), vehicle.component2())),
            starshipConnection = ConnectionDto(2, listOf(starship.component1(), starship.component2())),
            movieConnection = ConnectionDto(2, listOf(movies.component1(), movies.component2())),
            isFromLocalStore = true)

    val planetDto = PlanetDto(id = "", name = "planet", diameter = "1", gravity = "gravity",
            population = "100 000", rotationPeriod = "rotationPeriod", orbitalPeriod = "orbitalPeriod",
            climates = listOf("climate1", "climate2"), surfaceWater = "water",
            terrains = listOf("terrain1", "terrain2"),
            characterConnection = ConnectionDto(2, listOf(people.component1(), people.component2())),
            movieConnection = ConnectionDto(2, listOf(movies.component1(), movies.component2())),
            isFromLocalStore = true)

    val specieDto = SpecieDto(id = "", name = "specie", language = "language",
            homeworld = planet.component1(),
            classification = "classification", designation = "designation", averageHeight = "averageHeight",
            averageLifespan = "averageLifespan", eyeColors = listOf("eyeColor1", "eyeColor2"),
            hairColors = listOf("hairColor1", "hairColor2"), skinColors = listOf("skinColor1", "skinColor2"),
            characterConnection = ConnectionDto(2, listOf(people.component1(), people.component2())),
            movieConnection = ConnectionDto(2, listOf(movies.component1(), movies.component2())),
            isFromLocalStore = true)

    val starshipDto =
        StarshipDto(id = "", name = "title", model = "1", starshipClass = "height", cost = "cost",
                length = "length", cargoCapacity = "cargoCapacity",
                manufacturers = listOf("manufacturer1", "manufacturer2"),
                vMax = "vMax", hyperdriveRating = "hyperdriveRating", megalight = "megalight", crew = "crew",
                passengers = "pilot", consumables = "consumables",
                characterConnection = ConnectionDto(2, listOf(people.component1(), people.component2())),
                movieConnection = ConnectionDto(2, listOf(movies.component1(), movies.component2())),
                isFromLocalStore = true)

    val vehicleDto = VehicleDto(id = "", name = "title", model = "1", vehicleClass = "height",
            cost = "cost", length = "length", cargoCapacity = "cargoCapacity",
            manufacturers = listOf("manufacturer1", "manufacturer2"), vMax = "vMax", crew = "crew",
            passengers = "pilot", consumables = "consumables",
            characterConnection = ConnectionDto(2, listOf(people.component1(), people.component2())),
            movieConnection = ConnectionDto(2, listOf(movies.component1(), movies.component2())),
            isFromLocalStore = true)

    const val MOVIE_NAME = "MOVIES"
    const val PEOPLE_NAME = "PEOPLE"
    const val PLANET_NAME = "PLANETS"
    const val SPECIE_NAME = "SPECIES"
    const val STARSHIP_NAME = "STARSHIPS"
    const val VEHICLE_NAME = "VEHICLES"
}