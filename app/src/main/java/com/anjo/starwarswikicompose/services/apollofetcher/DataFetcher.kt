package com.anjo.starwarswikicompose.services.apollofetcher

import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.GetPersonQuery
import com.anjo.starwarswikicompose.GetPlanetQuery
import com.anjo.starwarswikicompose.GetSpecieQuery
import com.anjo.starwarswikicompose.GetStarshipQuery
import com.anjo.starwarswikicompose.GetVehicleQuery


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