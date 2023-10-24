package com.anjo.starwarswikicompose.services.data.repository

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
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcher
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApi
import com.anjo.starwarswikicompose.services.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val dataStore: DataStoreOperations,
        private val dataFetcher: DataFetcher,
        private val flickrApi:FlickrApi
) {
    suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.readingBoardingState()
    }

     suspend fun fetchFilms(): List<GetAllFilmsQuery.Film?>? {
       return dataFetcher.fetchFilms()
    }

     suspend fun fetchOneFilm(id: String): GetFilmQuery.Film? {
        return dataFetcher.fetchOneFilm(id)
    }

     suspend fun fetchPeoples(): List<GetAllPeoplesQuery.Person?>? {
       return dataFetcher.fetchPeoples()
    }

     suspend fun fetchOnePerson(id: String): GetPersonQuery.Person? {
       return dataFetcher.fetchOnePerson(id)
    }

     suspend fun fetchPlanets(): List<GetAllPlanetsQuery.Planet?>? {
        return dataFetcher.fetchPlanets()
    }

     suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet? {
        return dataFetcher.fetchOnePlanet(id)
    }

     suspend fun fetchSpecies(): List<GetAllSpeciesQuery.Species?>? {
        return dataFetcher.fetchSpecies()
    }

     suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species? {
        return dataFetcher.fetchOneSpecie(id)
    }

     suspend fun fetchStarships(): List<GetAllStarshipsQuery.Starship?>? {
        return dataFetcher.fetchStarships()
    }

     suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship? {
        return dataFetcher.fetchOneStarship(id)
    }

     suspend fun fetchVehicles(): List<GetAllVehiclesQuery.Vehicle?>? {
        return dataFetcher.fetchVehicles()
    }

     suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle? {
        return dataFetcher.fetchOneVehicle(id)
    }

     suspend fun getSearchPhotosInfo(searchText: String): FlickrResponse {
         return flickrApi.getSearchPhotosInfo(searchText = searchText)
    }

     suspend fun getRecentPhotos(): FlickrResponse {
        return flickrApi.getRecentPhotos()
    }
}