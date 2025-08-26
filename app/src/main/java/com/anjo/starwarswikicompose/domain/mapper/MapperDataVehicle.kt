package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.dto.emptyConnection
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.VehicleEntity
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun GetVehicleQuery.Vehicle.mapToVehicle(): VehicleDto {
    return VehicleDto(
            id = id,
            name = name ?: "",
            model = model ?: "",
            vehicleClass = vehicleClass ?: "",
            manufacturers = manufacturers?.filterNotNull() ?: listOf(),
            cost = costInCredits?.toString() ?: "",
            length = length?.toString() ?: "",
            crew = crew ?: "",
            passengers = passengers ?: "",
            vMax = maxAtmospheringSpeed?.toString() ?: "",
            consumables = consumables ?: "",
            cargoCapacity = cargoCapacity?.toString() ?: "",
            characterConnection = pilotConnection?.mapToConnection() ?: emptyConnection(),
            movieConnection = filmConnection?.mapToConnection() ?: emptyConnection()
    )
}

fun VehicleDto.toModel(): Vehicle {
    return Vehicle(
            entity = toEntity(),
            characterChunks = characterConnection.objects.map { it.toModel() },
            movieChunks = movieConnection.objects.map { it.toModel() },
    )
}

fun Vehicle.toDto(): VehicleDto {
    return VehicleDto(
            id = entity.id,
            name = entity.name,
            model = entity.model,
            vehicleClass = entity.vehicleClass,
            manufacturers = entity.manufacturers,
            cost = entity.cost,
            length = entity.length,
            crew = entity.crew,
            passengers = entity.passengers,
            vMax = entity.vMax,
            cargoCapacity = entity.cargoCapacity,
            consumables = entity.consumables,
            characterConnection = characterChunks.toConnectionDto(PEOPLE),
            movieConnection = movieChunks.toConnectionDto(FILMS),
            isFromLocalStore = true
    )
}

fun VehicleDto.toChunkDto(modelId: String, sourceType: SourceType): UniversalChunkDto {
    return UniversalChunkDto(
            id = modelId,
            name = name,
            desc = model,
            category = VEHICLES,
            sourceType = sourceType
    )
}

@OptIn(ExperimentalUuidApi::class)
private fun VehicleDto.toEntity(): VehicleEntity {
    return VehicleEntity(
            id = id.ifEmpty { Uuid.random().toString() },
            name = name,
            model = model,
            vehicleClass = vehicleClass,
            manufacturers = manufacturers,
            cost = cost,
            length = length,
            crew = crew,
            passengers = passengers,
            vMax = vMax,
            cargoCapacity = cargoCapacity,
            consumables = consumables,
            lastChanged = LocalDateTime.now()
    )
}

private fun GetVehicleQuery.FilmConnection.mapToConnection(): ConnectionDto {
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

private fun GetVehicleQuery.PilotConnection.mapToConnection(): ConnectionDto {
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