package com.anjo.starwarswikicompose.domain.model.sw

import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.ERROR
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

data class Movie(
        val id: String = "",
        val title: String = "",
        val episodeId: String = "",
        val openingCrawl: String = "",
        val producers: List<String> = listOf(),
        val director: String = "",
        val releaseDate: String = "",
        val characterConnection: Connection = emptyConnection(),
        val planetConnection: Connection = emptyConnection(),
        val starshipConnection: Connection = emptyConnection(),
        val vehicleConnection: Connection = emptyConnection(),
        val specieConnection: Connection = emptyConnection(),
)

data class Person(
        val id: String = "",
        val name: String = "",
        val homeworld: UniversalChunk = UniversalChunk(),
        val specie: UniversalChunk = UniversalChunk(),
        val birthYear: String = "",
        val height: String = "",
        val mass: String = "",
        val gender: String = "",
        val hair: String = "",
        val skin: String = "",
        val movieConnection: Connection = emptyConnection(),
        val starshipConnection: Connection = emptyConnection(),
        val vehicleConnection: Connection = emptyConnection(),
)

data class Planet(
        val id: String = "",
        val name: String = "",
        val diameter: String = "",
        val gravity: String = "",
        val population: String = "",
        val rotationPeriod: String = "",
        val orbitalPeriod: String = "",
        val climates: List<String> = listOf(),
        val surfaceWater: String = "",
        val terrains: List<String> = listOf(),
        val characterConnection: Connection = emptyConnection(),
        val movieConnection: Connection = emptyConnection(),
)

data class Specie(
        val id: String = "",
        val name: String = "",
        val language: String = "",
        val homeworld: UniversalChunk = UniversalChunk(),
        val classification: String = "",
        val designation: String = "",
        val averageHeight: String = "",
        val averageLifespan: String = "",
        val eyeColors: List<String> = listOf(),
        val hairColors: List<String> = listOf(),
        val skinColors: List<String> = listOf(),
        val characterConnection: Connection = emptyConnection(),
        val movieConnection: Connection = emptyConnection(),
)

data class Vehicle(
        val id: String = "",
        val name: String = "",
        val model: String = "",
        val vehicleClass: String = "",
        val manufacturers: List<String> = listOf(),
        val cost: String = "",
        val length: String = "",
        val crew: String = "",
        val passengers: String = "",
        val vMax: String = "",
        val cargoCapacity: String = "",
        val consumables: String = "",
        val characterConnection: Connection = emptyConnection(),
        val movieConnection: Connection = emptyConnection(),
)

data class Starship(
        val id: String = "",
        val name: String = "",
        val model: String = "",
        val starshipClass: String = "",
        val manufacturers: List<String> = listOf(),
        val cost: String = "",
        val length: String = "",
        val cargoCapacity: String = "",
        val vMax: String = "",
        val hyperdriveRating: String = "",
        val megalight: String = "",
        val crew: String = "",
        val passengers: String = "",
        val consumables: String = "",
        val characterConnection: Connection = emptyConnection(),
        val movieConnection: Connection = emptyConnection(),
)

data class MovieDetailState(val movie: Movie = Movie(), val state: DetailObjectState = ERROR)
data class PersonDetailState(val person: Person = Person(), val state: DetailObjectState = ERROR)
data class PlanetDetailState(val planet: Planet = Planet(), val state: DetailObjectState = ERROR)
data class SpecieDetailState(val specie: Specie = Specie(), val state: DetailObjectState = ERROR)
data class VehicleDetailState(val vehicle: Vehicle = Vehicle(), val state: DetailObjectState = ERROR)
data class StarshipsDetailState(val starship: Starship = Starship(), val state: DetailObjectState = ERROR)