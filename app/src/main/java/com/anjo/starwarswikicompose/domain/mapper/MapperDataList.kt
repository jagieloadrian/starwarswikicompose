package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.apollo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.apollo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.apollo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.apollo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.apollo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.SourceType

fun List<GetAllFilmsQuery.Film?>.mapFromFilms(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.title ?: "",
                        desc = it.episodeID.toString(),
                        category = FILMS,
                        sourceType = SourceType.APOLLO)
            }
}

fun List<GetAllPeoplesQuery.Person?>.mapFromPersons(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.name ?: "",
                        desc = it.birthYear ?: "",
                        category = PEOPLE,
                        sourceType = SourceType.APOLLO)
            }
}

fun List<GetAllPlanetsQuery.Planet?>.mapFromPlanets(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.name ?: "",
                        desc = formatPopulation((it.population?.toInt() ?: "").toString()),
                        category = PLANETS,
                        sourceType = SourceType.APOLLO)
            }
}

fun List<GetAllSpeciesQuery.Species?>.mapFromSpecies(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.name ?: "",
                        desc = it.language ?: "",
                        category = SPECIES,
                        sourceType = SourceType.APOLLO)
            }
}

fun List<GetAllVehiclesQuery.Vehicle?>.mapFromVehicles(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.name ?: "",
                        desc = it.model ?: "",
                        category = VEHICLES,
                        sourceType = SourceType.APOLLO)
            }
}

fun List<GetAllStarshipsQuery.Starship?>.mapFromStarships(): List<UniversalChunkDto> {
    return this.filterNotNull()
            .map {
                UniversalChunkDto(id = it.id,
                        name = it.name ?: "",
                        desc = it.model ?: "",
                        category = STARSHIPS,
                        sourceType = SourceType.APOLLO)
            }
}

fun formatPopulation(population: String): String {
    if (population.isEmpty()) {
        return "0 citizens"
    }
    return "$population citizens"
}