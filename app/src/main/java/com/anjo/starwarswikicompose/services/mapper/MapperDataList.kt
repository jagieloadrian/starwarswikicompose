package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk

fun List<GetAllFilmsQuery.Film?>.mapFromFilms(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.title ?: "",
                        desc = it.episodeID.toString())
            }
}

fun List<GetAllPeoplesQuery.Person?>.mapFromPersons(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.name ?: "",
                        desc = it.birthYear ?: "")
            }
}

fun List<GetAllPlanetsQuery.Planet?>.mapFromPlanets(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.name ?: "",
                        desc = (it.population ?: "").toString())
            }
}

fun List<GetAllSpeciesQuery.Species?>.mapFromSpecies(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.name ?: "",
                        desc = it.language ?: "")
            }
}

fun List<GetAllVehiclesQuery.Vehicle?>.mapFromVehicles(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.name ?: "",
                        desc = it.model ?: "")
            }
}

fun List<GetAllStarshipsQuery.Starship?>.mapFromStarships(): List<UniversalChunk> {
    return this.filterNotNull()
            .map {
                UniversalChunk(id = it.id,
                        name = it.name ?: "",
                        desc = it.model ?: "")
            }
}

fun formatPopulation(population: String): String {
    if (population.isEmpty()) {
        return "0 citizens"
    }
    return "$population citizens"
}