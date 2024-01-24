package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetStarshipQuery
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetStarshipQuery.Starship.mapToStarship(): Starship {
    return Starship(
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

private fun GetStarshipQuery.FilmConnection.mapToConnection(): Connection {
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

private fun GetStarshipQuery.PilotConnection.mapToConnection(): Connection {
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
