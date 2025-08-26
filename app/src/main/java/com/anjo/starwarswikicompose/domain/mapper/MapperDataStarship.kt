package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetStarshipQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.StarshipEntity
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetStarshipQuery.Starship.mapToStarship(): StarshipDto {
    return StarshipDto(
            id = id,
            name = name ?: "",
            model = model ?: "",
            starshipClass = starshipClass ?: "",
            manufacturers = manufacturers?.filterNotNull() ?: listOf(),
            cost = costInCredits?.toString() ?: "",
            length = length?.toString() ?: "",
            crew = crew ?: "",
            passengers = passengers ?: "",
            vMax = maxAtmospheringSpeed?.toString() ?: "",
            consumables = consumables ?: "",
            hyperdriveRating = hyperdriveRating?.toString() ?: "",
            megalight = MGLT?.toString() ?: "",
            cargoCapacity = cargoCapacity?.toString() ?: "",
            characterConnection = pilotConnection?.mapToConnection() ?: emptyConnection(),
            movieConnection = filmConnection?.mapToConnection() ?: emptyConnection()
    )
}

fun StarshipDto.toModel(): Starship {
    return Starship(
            entity = toEntity(),
            characterChunks = characterConnection.objects.map { it.toModel() },
            movieChunks = movieConnection.objects.map { it.toModel() },
    )
}

fun Starship.toDto(): StarshipDto {
    return StarshipDto(
            id = entity.id,
            name = entity.name,
            model = entity.model,
            starshipClass = entity.starshipClass,
            manufacturers = entity.manufacturers,
            cost = entity.cost,
            length = entity.length,
            crew = entity.crew,
            passengers = entity.passengers,
            vMax = entity.vMax,
            cargoCapacity = entity.cargoCapacity,
            consumables = entity.consumables,
            hyperdriveRating = entity.hyperdriveRating,
            megalight = entity.megalight,
            characterConnection = characterChunks.toConnectionDto(PEOPLE),
            movieConnection = movieChunks.toConnectionDto(FILMS),
            isFromLocalStore = true
    )
}

fun StarshipDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = name,
            desc = model,
            category = STARSHIPS,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun StarshipDto.toEntity(): StarshipEntity {
    return StarshipEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            name = name,
            model = model,
            starshipClass = starshipClass,
            manufacturers = manufacturers,
            cost = cost,
            length = length,
            cargoCapacity = cargoCapacity,
            vMax = vMax,
            hyperdriveRating = hyperdriveRating,
            megalight = megalight,
            crew = crew,
            passengers = passengers,
            consumables = consumables,
            lastChanged = LocalDateTime.now()
    )
}

private fun GetStarshipQuery.FilmConnection.mapToConnection(): ConnectionDto {
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

private fun GetStarshipQuery.PilotConnection.mapToConnection(): ConnectionDto {
    return ConnectionDto(
            totalCount = totalCount ?: 0,
            objects = pilots?.filterNotNull()
                    ?.map { pilot ->
                        UniversalChunkDto(
                                id = pilot.id,
                                name = pilot.name ?: "",
                                category = PEOPLE,
                                sourceType = SourceType.APOLLO
                        )
                    } ?: emptyList()
    )
}
