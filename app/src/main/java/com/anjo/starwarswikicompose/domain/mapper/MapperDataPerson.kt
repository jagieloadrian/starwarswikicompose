package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetPersonQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.PersonEntity
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetPersonQuery.Person.mapToPerson(): PersonDto {
    return PersonDto(
            id = id,
            name = name ?: "",
            homeworld = homeworld?.mapToChunk() ?: UniversalChunkDto(category = PLANETS),
            specie = species?.mapToChunk() ?: UniversalChunkDto(category = SPECIES),
            birthYear = birthYear ?: "",
            height = height?.toString() ?: "",
            mass = mass?.toString() ?: "",
            gender = gender ?: "",
            hair = hairColor ?: "",
            skin = skinColor ?: "",
            movieConnection = filmConnection?.mapToConnection() ?: emptyConnection(),
            starshipConnection = starshipConnection?.mapToConnection() ?: emptyConnection(),
            vehicleConnection = vehicleConnection?.mapToConnection() ?: emptyConnection(),
    )
}

fun Person.toDto(): PersonDto {
    return PersonDto(id = entity.id,
            name = entity.name,
            homeworld = homeworld.toDtoByRoom(),
            specie = specie.toDtoByRoom(),
            birthYear = entity.birthYear,
            height = entity.height,
            mass = entity.mass,
            gender = entity.gender,
            hair = entity.hair,
            skin = entity.skin,
            movieConnection = movieChunks.toConnectionDto(FILMS),
            starshipConnection = starshipChunks.toConnectionDto(STARSHIPS),
            vehicleConnection = vehicleChunks.toConnectionDto(VEHICLES),
            isFromLocalStore = true)
}

fun PersonDto.toModel(homeworldId: String, specieId: String): Person {
    return Person(
            entity = toEntity(homeworldId, specieId),
            homeworld = homeworld.toModel(),
            specie = specie.toModel(),
            movieChunks = movieConnection.objects.map { it.toModel() },
            starshipChunks = starshipConnection.objects.map { it.toModel() },
            vehicleChunks = vehicleConnection.objects.map { it.toModel() },
    )
}

fun PersonDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = name,
            desc = birthYear,
            category = PEOPLE,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun PersonDto.toEntity(homeworldId: String, specieId: String): PersonEntity {
    return PersonEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            name = name,
            birthYear = birthYear,
            height = height,
            mass = mass,
            gender = gender,
            hair = hair,
            skin = skin,
            homeworldId = homeworldId,
            specieId = specieId,
            lastChanged = LocalDateTime.now()
    )
}

private fun GetPersonQuery.VehicleConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = vehicles?.filterNotNull()
                    ?.map { vehicle ->
                        UniversalChunkDto(
                                id = vehicle.id,
                                name = vehicle.name ?: "",
                                category = VEHICLES,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}

private fun GetPersonQuery.StarshipConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = starships?.filterNotNull()
                    ?.map { starship ->
                        UniversalChunkDto(
                                id = starship.id,
                                name = starship.name ?: "",
                                category = STARSHIPS,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}

private fun GetPersonQuery.FilmConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = films?.filterNotNull()
                    ?.map { film ->
                        UniversalChunkDto(
                                id = film.id,
                                name = film.title ?: "",
                                category = FILMS,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}

private fun GetPersonQuery.Species.mapToChunk(): UniversalChunkDto {
    return UniversalChunkDto(
            id = this.id,
            name = this.name ?: "",
            category = SPECIES,
            sourceType = SourceType.APOLLO
    )
}

private fun GetPersonQuery.Homeworld.mapToChunk(): UniversalChunkDto {
    return UniversalChunkDto(
            id = this.id,
            name = this.name ?: "",
            category = PLANETS,
            sourceType = SourceType.APOLLO
    )
}
