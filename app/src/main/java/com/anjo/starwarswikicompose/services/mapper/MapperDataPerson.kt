package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetPersonQuery
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetPersonQuery.Person.mapToPerson(): Person {
    return Person(
            id = id,
            name = name ?: "",
            homeworld = homeworld?.mapToChunk() ?: UniversalChunk(),
            specie = species?.mapToChunk() ?: UniversalChunk(),
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

private fun GetPersonQuery.VehicleConnection.mapToConnection(): Connection {
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

private fun GetPersonQuery.StarshipConnection.mapToConnection(): Connection {
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

private fun GetPersonQuery.FilmConnection.mapToConnection(): Connection {
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

private fun GetPersonQuery.Species.mapToChunk(): UniversalChunk {
    return UniversalChunk(
            id = this.id,
            name = this.name ?: ""
    )
}

private fun GetPersonQuery.Homeworld.mapToChunk(): UniversalChunk {
    return UniversalChunk(
            id = this.id,
            name = this.name ?: ""
    )
}
