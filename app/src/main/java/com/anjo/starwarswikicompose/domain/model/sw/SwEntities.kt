@file:OptIn(ExperimentalUuidApi::class)

package com.anjo.starwarswikicompose.domain.model.sw

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.anjo.starwarswikicompose.utils.Constants.MOVIE_TABLE
import com.anjo.starwarswikicompose.utils.Constants.PERSON_TABLE
import com.anjo.starwarswikicompose.utils.Constants.PLANET_TABLE
import com.anjo.starwarswikicompose.utils.Constants.SPECIE_TABLE
import com.anjo.starwarswikicompose.utils.Constants.STARSHIP_TABLE
import com.anjo.starwarswikicompose.utils.Constants.VEHICLE_TABLE
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
        val title: String = "",
        val episodeId: String = "",
        val openingCrawl: String = "",
        val producers: List<String> = listOf(),
        val director: String = "",
        val releaseDate: String = "",
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = PERSON_TABLE, foreignKeys = [
    ForeignKey(
            entity = UniversalChunk::class,
            parentColumns = ["id"],
            childColumns = ["homeworldId"]
    ),
    ForeignKey(
            entity = UniversalChunk::class,
            parentColumns = ["id"],
            childColumns = ["specieId"]
    )
], indices = [
    Index(value = ["homeworldId"]),
    Index(value = ["specieId"])
])
data class PersonEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
        val name: String = "",
        val birthYear: String = "",
        val height: String = "",
        val mass: String = "",
        val gender: String = "",
        val hair: String = "",
        val skin: String = "",
        val homeworldId: String = "",
        val specieId: String = "",
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = PLANET_TABLE)
data class PlanetEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
        val name: String = "",
        val diameter: String = "",
        val gravity: String = "",
        val population: String = "",
        val rotationPeriod: String = "",
        val orbitalPeriod: String = "",
        val climates: List<String> = listOf(),
        val surfaceWater: String = "",
        val terrains: List<String> = listOf(),
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = SPECIE_TABLE, foreignKeys = [
    ForeignKey(
            entity = UniversalChunk::class,
            parentColumns = ["id"],
            childColumns = ["homeworldId"]
    )
], indices = [
    Index(value = ["homeworldId"])
])
data class SpecieEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
        val name: String = "",
        val language: String = "",
        val classification: String = "",
        val designation: String = "",
        val averageHeight: String = "",
        val averageLifespan: String = "",
        val eyeColors: List<String> = listOf(),
        val hairColors: List<String> = listOf(),
        val skinColors: List<String> = listOf(),
        val homeworldId: String = "",
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = VEHICLE_TABLE)
data class VehicleEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
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
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = STARSHIP_TABLE)
data class StarshipEntity(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
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
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)