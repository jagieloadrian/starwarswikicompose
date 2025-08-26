package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetSpecieQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.SpecieEntity
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetSpecieQuery.Species.mapToSpecie(): SpecieDto {
    return SpecieDto(
            id = id,
            name = name ?: "",
            language = language ?: "",
            homeworld = homeworld?.mapToChunk() ?: UniversalChunkDto(category = PLANETS),
            classification = classification ?: "",
            designation = designation ?: "",
            averageHeight = averageHeight?.toString() ?: "",
            averageLifespan = averageLifespan?.toString() ?: "",
            eyeColors = eyeColors?.filterNotNull() ?: listOf(),
            hairColors = hairColors?.filterNotNull() ?: listOf(),
            skinColors = skinColors?.filterNotNull() ?: listOf(),
            characterConnection = personConnection?.mapToConnection() ?: emptyConnection(),
            movieConnection = filmConnection?.mapToConnection() ?: emptyConnection(),
    )
}

fun Specie.toDto(): SpecieDto {
    return SpecieDto(id = entity.id,
            name = entity.name,
            language = entity.language,
            homeworld = homeworld.toDtoByRoom(),
            classification = entity.classification,
            designation = entity.designation,
            averageHeight = entity.averageHeight,
            averageLifespan = entity.averageLifespan,
            eyeColors = entity.eyeColors,
            hairColors = entity.hairColors,
            skinColors = entity.skinColors,
            characterConnection = characterChunks.toConnectionDto(PEOPLE),
            movieConnection = movieChunks.toConnectionDto(FILMS),
            isFromLocalStore = true)
}

fun SpecieDto.toModel(homeworldId: String): Specie {
    return Specie(
            entity = toEntity(homeworldId),
            homeworld = homeworld.toModel(),
            characterChunks = characterConnection.objects.map { it.toModel() },
            movieChunks = movieConnection.objects.map { it.toModel() },
    )
}

fun SpecieDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = name,
            desc = language,
            category = SPECIES,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun SpecieDto.toEntity(homeworldId: String): SpecieEntity {
    return SpecieEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            name = name,
            language = language,
            classification = classification,
            designation = designation,
            averageHeight = averageHeight,
            averageLifespan = averageLifespan,
            eyeColors = eyeColors,
            hairColors = hairColors,
            skinColors = skinColors,
            homeworldId = homeworldId,
            lastChanged = LocalDateTime.now()
    )
}

private fun GetSpecieQuery.FilmConnection.mapToConnection(): ConnectionDto {
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

private fun GetSpecieQuery.PersonConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = people?.filterNotNull()
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

private fun GetSpecieQuery.Homeworld.mapToChunk(): UniversalChunkDto {
    return UniversalChunkDto(
            id = this.id,
            name = this.name ?: "",
            category = PLANETS,
            sourceType = SourceType.APOLLO
    )
}
