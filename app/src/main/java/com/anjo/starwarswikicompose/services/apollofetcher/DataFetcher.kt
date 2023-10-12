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
    suspend fun fetchFilms(): GetAllFilmsQuery.AllFilms?
    suspend fun fetchOneFilm(id: String): GetFilmQuery.Film?
    suspend fun fetchPeoples(): GetAllPeoplesQuery.AllPeople?
    suspend fun fetchOnePerson(id: String): GetPersonQuery.Person?
    suspend fun fetchPlanets(): GetAllPlanetsQuery.AllPlanets?
    suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet?
    suspend fun fetchSpecies(): GetAllSpeciesQuery.AllSpecies?
    suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species?
    suspend fun fetchStarships(): GetAllStarshipsQuery.AllStarships?
    suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship?
    suspend fun fetchVehicles(): GetAllVehiclesQuery.AllVehicles?
    suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle?
}