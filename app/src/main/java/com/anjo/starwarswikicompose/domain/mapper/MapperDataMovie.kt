package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetFilmQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.MovieEntity
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetFilmQuery.Film.mapToMovie(): MovieDto {
    return MovieDto(
            id = id,
            title = title ?: "",
            openingCrawl = openingCrawl ?: "",
            releaseDate = releaseDate ?: "",
            producers = producers?.filterNotNull() ?: listOf(),
            director = director ?: "",
            episodeId = episodeID?.toString() ?: "",
            characterConnection = characterConnection?.mapToConnection() ?: emptyConnection(),
            planetConnection = planetConnection?.mapToConnection() ?: emptyConnection(),
            vehicleConnection = vehicleConnection?.mapToConnection() ?: emptyConnection(),
            starshipConnection = starshipConnection?.mapToConnection() ?: emptyConnection(),
            specieConnection = speciesConnection?.mapToConnection() ?: emptyConnection()
    )
}

fun Movie.toDto(): MovieDto {
    return MovieDto(id = entity.id,
            title = entity.title,
            episodeId = entity.episodeId,
            openingCrawl = entity.openingCrawl,
            producers = entity.producers,
            director = entity.director,
            releaseDate = entity.releaseDate,
            characterConnection = characterChunks.toConnectionDto(PEOPLE),
            planetConnection = planetChunks.toConnectionDto(PLANETS),
            starshipConnection = starshipChunks.toConnectionDto(STARSHIPS),
            vehicleConnection = vehicleChunks.toConnectionDto(VEHICLES),
            specieConnection = specieChunks.toConnectionDto(SPECIES),
            isFromLocalStore = true)
}

fun MovieDto.toModel(): Movie {
    return Movie(
            entity = toEntity(),
            characterChunks = characterConnection.objects.map { it.toModel() },
            planetChunks = planetConnection.objects.map { it.toModel() },
            starshipChunks = starshipConnection.objects.map { it.toModel() },
            vehicleChunks = vehicleConnection.objects.map { it.toModel() },
            specieChunks = specieConnection.objects.map { it.toModel() },
    )
}

fun MovieDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = title,
            desc = episodeId,
            category = FILMS,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            title = title,
            episodeId = episodeId,
            openingCrawl = openingCrawl,
            producers = producers,
            director = director,
            releaseDate = releaseDate,
            lastChanged = LocalDateTime.now(),
    )
}

private fun GetFilmQuery.SpeciesConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = species?.filterNotNull()
                    ?.map { specie ->
                        UniversalChunkDto(
                                id = specie.id,
                                name = specie.name ?: "",
                                category = SPECIES,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.StarshipConnection.mapToConnection(): ConnectionDto {
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

private fun GetFilmQuery.VehicleConnection.mapToConnection(): ConnectionDto {
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

private fun GetFilmQuery.PlanetConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = planets?.filterNotNull()
                    ?.map { planet ->
                        UniversalChunkDto(
                                id = planet.id,
                                name = planet.name ?: "",
                                category = PLANETS,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.CharacterConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = characters?.filterNotNull()
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