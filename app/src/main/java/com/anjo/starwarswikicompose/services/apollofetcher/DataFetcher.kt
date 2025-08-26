package com.anjo.starwarswikicompose.services.apollofetcher

import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto


interface DataFetcher {
    suspend fun fetchFilms(): List<UniversalChunkDto>
    suspend fun fetchOneFilm(id: String): MovieDto?
    suspend fun fetchPeoples(): List<UniversalChunkDto>
    suspend fun fetchOnePerson(id: String): PersonDto?
    suspend fun fetchPlanets(): List<UniversalChunkDto>
    suspend fun fetchOnePlanet(id: String): PlanetDto?
    suspend fun fetchSpecies(): List<UniversalChunkDto>
    suspend fun fetchOneSpecie(id: String): SpecieDto?
    suspend fun fetchStarships(): List<UniversalChunkDto>
    suspend fun fetchOneStarship(id: String): StarshipDto?
    suspend fun fetchVehicles(): List<UniversalChunkDto>
    suspend fun fetchOneVehicle(id: String): VehicleDto?
}