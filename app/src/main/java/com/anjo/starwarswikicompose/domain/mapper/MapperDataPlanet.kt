package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetPlanetQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.PlanetEntity
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetPlanetQuery.Planet.mapToPlanet(): PlanetDto {
    return PlanetDto(
            id = id,
            name = name ?: "",
            diameter = diameter?.toString() ?: "",
            gravity = gravity ?: "",
            population = population?.toString() ?: "",
            rotationPeriod = rotationPeriod?.toString() ?: "",
            orbitalPeriod = orbitalPeriod?.toString() ?: "",
            climates = climates?.filterNotNull() ?: listOf(),
            surfaceWater = surfaceWater?.toString() ?: "",
            terrains = terrains?.filterNotNull() ?: listOf(),
            characterConnection = residentConnection?.mapToConnection() ?: emptyConnection(),
            movieConnection = filmConnection?.mapToConnection() ?: emptyConnection()
    )
}

fun Planet.toDto(): PlanetDto {
    return PlanetDto(id = entity.id,
            name = entity.name,
            diameter = entity.diameter,
            gravity = entity.gravity,
            population = entity.population,
            rotationPeriod = entity.rotationPeriod,
            orbitalPeriod = entity.orbitalPeriod,
            climates = entity.climates,
            surfaceWater = entity.surfaceWater,
            terrains = entity.terrains,
            characterConnection = characterChunks.toConnectionDto(PEOPLE),
            movieConnection = movieChunks.toConnectionDto(FILMS),
            isFromLocalStore = true
    )
}

fun PlanetDto.toModel(): Planet {
    return Planet(
            entity = toEntity(),
            characterChunks = characterConnection.objects.map { it.toModel() },
            movieChunks = movieConnection.objects.map { it.toModel() },
    )
}

fun PlanetDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = name,
            desc = population,
            category = PLANETS,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun PlanetDto.toEntity(): PlanetEntity {
    return PlanetEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            name = name,
            diameter = diameter,
            gravity = gravity,
            population = population,
            rotationPeriod = rotationPeriod,
            orbitalPeriod = orbitalPeriod,
            climates = climates,
            surfaceWater = surfaceWater,
            terrains = terrains,
            lastChanged = LocalDateTime.now())
}

private fun GetPlanetQuery.FilmConnection.mapToConnection(): ConnectionDto {
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

private fun GetPlanetQuery.ResidentConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = residents?.filterNotNull()
                    ?.map { character ->
                        UniversalChunkDto(
                                id = character.id,
                                name = character.name ?: "",
                                category = PEOPLE,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}
