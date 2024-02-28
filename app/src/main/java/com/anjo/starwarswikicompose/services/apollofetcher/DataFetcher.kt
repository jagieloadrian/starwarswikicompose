package com.anjo.starwarswikicompose.services.apollofetcher

import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk


interface DataFetcher {
    suspend fun fetchFilms(): List<UniversalChunk>
    suspend fun fetchOneFilm(id: String): Movie?
    suspend fun fetchPeoples(): List<UniversalChunk>
    suspend fun fetchOnePerson(id: String): Person?
    suspend fun fetchPlanets(): List<UniversalChunk>
    suspend fun fetchOnePlanet(id: String): Planet?
    suspend fun fetchSpecies(): List<UniversalChunk>
    suspend fun fetchOneSpecie(id: String): Specie?
    suspend fun fetchStarships(): List<UniversalChunk>
    suspend fun fetchOneStarship(id: String): Starship?
    suspend fun fetchVehicles(): List<UniversalChunk>
    suspend fun fetchOneVehicle(id: String): Vehicle?
}