package com.anjo.starwarswikicompose.domain.dto

import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.ERROR

data class MovieDto(
        val id: String = "",
        val title: String = "",
        val episodeId: String = "",
        val openingCrawl: String = "",
        val producers: List<String> = listOf(),
        val director: String = "",
        val releaseDate: String = "",
        val characterConnection: ConnectionDto = emptyConnection(),
        val planetConnection: ConnectionDto = emptyConnection(),
        val starshipConnection: ConnectionDto = emptyConnection(),
        val vehicleConnection: ConnectionDto = emptyConnection(),
        val specieConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class PersonDto(
        val id: String = "",
        val name: String = "",
        val homeworld: UniversalChunkDto = UniversalChunkDto(category = Category.PLANETS),
        val specie: UniversalChunkDto = UniversalChunkDto(category = Category.SPECIES),
        val birthYear: String = "",
        val height: String = "",
        val mass: String = "",
        val gender: String = "",
        val hair: String = "",
        val skin: String = "",
        val movieConnection: ConnectionDto = emptyConnection(),
        val starshipConnection: ConnectionDto = emptyConnection(),
        val vehicleConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class PlanetDto(
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
        val characterConnection: ConnectionDto = emptyConnection(),
        val movieConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class SpecieDto(
        val id: String = "",
        val name: String = "",
        val language: String = "",
        val homeworld: UniversalChunkDto = UniversalChunkDto(category = Category.PLANETS),
        val classification: String = "",
        val designation: String = "",
        val averageHeight: String = "",
        val averageLifespan: String = "",
        val eyeColors: List<String> = listOf(),
        val hairColors: List<String> = listOf(),
        val skinColors: List<String> = listOf(),
        val characterConnection: ConnectionDto = emptyConnection(),
        val movieConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class VehicleDto(
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
        val characterConnection: ConnectionDto = emptyConnection(),
        val movieConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class StarshipDto(
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
        val characterConnection: ConnectionDto = emptyConnection(),
        val movieConnection: ConnectionDto = emptyConnection(),
        val isFromLocalStore: Boolean = false,
)

data class MovieDetailState(val movieDto: MovieDto = MovieDto(), val state: DetailObjectState = ERROR)
data class PersonDetailState(val personDto: PersonDto = PersonDto(), val state: DetailObjectState = ERROR)
data class PlanetDetailState(val planetDto: PlanetDto = PlanetDto(), val state: DetailObjectState = ERROR)
data class SpecieDetailState(val specieDto: SpecieDto = SpecieDto(), val state: DetailObjectState = ERROR)
data class VehicleDetailState(val vehicleDto: VehicleDto = VehicleDto(), val state: DetailObjectState = ERROR)
data class StarshipsDetailState(val starshipDto: StarshipDto = StarshipDto(), val state: DetailObjectState = ERROR)