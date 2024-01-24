package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetVehicleQuery.Vehicle.mapToVehicle(): Vehicle {
    return Vehicle(
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

private fun GetVehicleQuery.FilmConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = films?.filterNotNull()
                    ?.map { film ->
                        UniversalChunk(
                                id = film.id,
                                name = film.title ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetVehicleQuery.PilotConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = pilots?.filterNotNull()
                    ?.map { pilot ->
                        UniversalChunk(
                                id = pilot.id,
                                name = pilot.name ?: ""
                        )
                    } ?: emptyList()
    )
}