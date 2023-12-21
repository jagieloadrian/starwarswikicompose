package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetPlanetQuery
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetPlanetQuery.Planet.mapToPlanet() : Planet {
    return Planet(
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

private fun GetPlanetQuery.FilmConnection.mapToConnection(): Connection {
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

private fun GetPlanetQuery.ResidentConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = residents?.filterNotNull()
                    ?.map { character ->
                        UniversalChunk(
                                id = character.id,
                                name = character.name ?: ""
                        )
                    } ?: emptyList()
    )
}
