package com.anjo.starwarswikicompose.services.apollofetcher

import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.GetFilmQuery
import com.anjo.GetPersonQuery
import com.anjo.GetPlanetQuery
import com.anjo.GetSpecieQuery
import com.anjo.GetStarshipQuery
import com.anjo.GetVehicleQuery

interface DataFetcher {
    suspend fun fetchFilms(): List<GetAllFilmsQuery.Film?>?
    suspend fun fetchOneFilm(id: String): GetFilmQuery.Film?
    suspend fun fetchPeoples(): List<GetAllPeoplesQuery.Person?>?
    suspend fun fetchOnePerson(id: String): GetPersonQuery.Person?
    suspend fun fetchPlanets(): List<GetAllPlanetsQuery.Planet?>?
    suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet?
    suspend fun fetchSpecies(): List<GetAllSpeciesQuery.Species?>?
    suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species?
    suspend fun fetchStarships(): List<GetAllStarshipsQuery.Starship?>?
    suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship?
    suspend fun fetchVehicles(): List<GetAllVehiclesQuery.Vehicle?>?
    suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle?
}