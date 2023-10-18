package com.anjo.starwarswikicompose.services.apollofetcher

import android.util.Log
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
import com.anjo.starwarswikicompose.utils.Category
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import kotlinx.coroutines.flow.Flow

class DataFetcherImpl(private val apolloClient: ApolloClient) : DataFetcher {
    override suspend fun fetchFilms(): GetAllFilmsQuery.AllFilms? {
        return getResponse(GetAllFilmsQuery(), Category.FILMS)?.data?.allFilms
    }

    override suspend fun fetchOneFilm(id: String): GetFilmQuery.Film? {
        val query = GetFilmQuery(id = Optional.presentIfNotNull(id))
        val getFilm = getResponse(query, Category.FILMS)
        return getFilm?.data?.film
    }

    override suspend fun fetchPeoples(): GetAllPeoplesQuery.AllPeople? {
        val getPeoples = getResponse(GetAllPeoplesQuery(), Category.PEOPLE)
        return getPeoples?.data?.allPeople
    }

    override suspend fun fetchOnePerson(id: String): GetPersonQuery.Person? {
        val query = GetPersonQuery(id = Optional.presentIfNotNull(id))
        val getPerson = getResponse(query, Category.PEOPLE)
        return getPerson?.data?.person
    }

    override suspend fun fetchPlanets(): GetAllPlanetsQuery.AllPlanets? {
        val getPlanets = getResponse(GetAllPlanetsQuery(), Category.PLANETS)
        return getPlanets?.data?.allPlanets
    }

    override suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet? {
        val query = GetPlanetQuery(id = Optional.presentIfNotNull(id))
        val getPlanet = getResponse(query, Category.PLANETS)
        return getPlanet?.data?.planet
    }

    override suspend fun fetchSpecies(): GetAllSpeciesQuery.AllSpecies? {
        val getSpecies = getResponse(GetAllSpeciesQuery(), Category.SPECIES)
        return getSpecies?.data?.allSpecies
    }

    override suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species? {
        val query = GetSpecieQuery(id = Optional.presentIfNotNull(id))
        val getSpecie = getResponse(query, Category.SPECIES)
        return getSpecie?.data?.species
    }

    override suspend fun fetchStarships(): GetAllStarshipsQuery.AllStarships? {
        val getStarships = getResponse(GetAllStarshipsQuery(), Category.STARSHIPS)
        return getStarships?.data?.allStarships
    }

    override suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship? {
        val query = GetStarshipQuery(id = Optional.presentIfNotNull(id))
        val getStarship = getResponse(query, Category.STARSHIPS)
        return getStarship?.data?.starship
    }

    override suspend fun fetchVehicles(): GetAllVehiclesQuery.AllVehicles? {
        val getVehicles = getResponse(GetAllVehiclesQuery(), Category.VEHICLES)
        return getVehicles?.data?.allVehicles
    }

    override suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle? {
        val query = GetVehicleQuery(Optional.presentIfNotNull(id))
        val getVehicle = getResponse(query, Category.VEHICLES)
        return getVehicle?.data?.vehicle
    }

    private suspend fun <D : Query.Data> getResponse(query: Query<D>, enum: Category): ApolloResponse<D>? {
        try {
            return apolloClient.query(query = query).execute()
        } catch (exc: Exception) {
            Log.e("exception", "$exc for ${enum.name}")
        }
        return null
    }

    private suspend fun <D : Query.Data> getResponseFlow(query: Query<D>, enum: Category): Flow<ApolloResponse<D>>? {
        try {
            return apolloClient.query(query = query).toFlow()
        } catch (exc: Exception) {
            Log.e("exception", "$exc for ${enum.name}")
        }
        return null
    }
}
