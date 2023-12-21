package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetFilmQuery.Film.mapToMovie(): Movie {
    return Movie(
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

private fun GetFilmQuery.SpeciesConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = species?.filterNotNull()
                    ?.map { specie ->
                        UniversalChunk(
                                id = specie.id,
                                name = specie.name ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.StarshipConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = starships?.filterNotNull()
                    ?.map { starship ->
                        UniversalChunk(
                                id = starship.id,
                                name = starship.name ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.VehicleConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = vehicles?.filterNotNull()
                    ?.map { vehicle ->
                        UniversalChunk(
                                id = vehicle.id,
                                name = vehicle.name ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.PlanetConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = planets?.filterNotNull()
                    ?.map { planet ->
                        UniversalChunk(
                                id = planet.id,
                                name = planet.name ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetFilmQuery.CharacterConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = characters?.filterNotNull()
                    ?.map { character ->
                        UniversalChunk(
                                id = character.id,
                                name = character.name ?: ""
                        )
                    } ?: emptyList()
    )
}