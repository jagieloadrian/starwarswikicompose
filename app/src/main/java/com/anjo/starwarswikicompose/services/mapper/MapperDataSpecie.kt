package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetSpecieQuery
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.utils.emptyConnection

fun GetSpecieQuery.Species.mapToSpecie(): Specie {
    return Specie(
            id = id,
            name = name ?: "",
            language = language ?: "",
            homeworld = homeworld?.mapToChunk() ?: UniversalChunk(),
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

private fun GetSpecieQuery.FilmConnection.mapToConnection(): Connection {
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

private fun GetSpecieQuery.PersonConnection.mapToConnection(): Connection {
    return Connection(
            totalCount = totalCount ?: 0,
            objects = people?.filterNotNull()
                    ?.map { character ->
                        UniversalChunk(
                                id = character.id,
                                name = character.name ?: ""
                        )
                    } ?: emptyList()
    )
}

private fun GetSpecieQuery.Homeworld.mapToChunk(): UniversalChunk {
    return UniversalChunk(
            id = this.id,
            name = this.name ?: ""
    )
}
