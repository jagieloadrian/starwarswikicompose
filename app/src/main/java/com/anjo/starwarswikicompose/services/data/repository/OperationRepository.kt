package com.anjo.starwarswikicompose.services.data.repository


import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcher
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val dataStore: DataStoreOperations,
        private val dataFetcher: DataFetcher,
        private val flickrApi: FlickrApi,
) {
    suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.readingBoardingState()
    }

    suspend fun fetchFilms(): List<UniversalChunk> {
        return dataFetcher.fetchFilms()
    }

    suspend fun fetchOneFilm(id: String): Movie {
        return dataFetcher.fetchOneFilm(id)
    }

    suspend fun fetchPeoples(): List<UniversalChunk> {
        return dataFetcher.fetchPeoples()
    }

    suspend fun fetchOnePerson(id: String): Person  {
        return dataFetcher.fetchOnePerson(id)
    }

    suspend fun fetchPlanets(): List<UniversalChunk> {
        return dataFetcher.fetchPlanets()
    }

    suspend fun fetchOnePlanet(id: String):Planet {
        return dataFetcher.fetchOnePlanet(id)
    }

    suspend fun fetchSpecies(): List<UniversalChunk> {
        return dataFetcher.fetchSpecies()
    }

    suspend fun fetchOneSpecie(id: String): Specie {
        return dataFetcher.fetchOneSpecie(id)
    }

    suspend fun fetchStarships(): List<UniversalChunk> {
        return dataFetcher.fetchStarships()
    }

    suspend fun fetchOneStarship(id: String): Starship {
        return dataFetcher.fetchOneStarship(id)
    }

    suspend fun fetchVehicles(): List<UniversalChunk> {
        return dataFetcher.fetchVehicles()
    }

    suspend fun fetchOneVehicle(id: String): Vehicle {
        return dataFetcher.fetchOneVehicle(id)
    }

    suspend fun getSearchPhotosInfo(searchText: String): FlickrResponse {
        return flickrApi.getSearchPhotosInfo(searchText = searchText)
    }

    suspend fun getRecentPhotos(): FlickrResponse {
        return flickrApi.getRecentPhotos()
    }
}