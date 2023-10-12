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
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query

class DataFetcherImpl(private val apolloClient: ApolloClient) : DataFetcher {
    override suspend fun fetchFilms(): GetAllFilmsQuery.AllFilms? {
        val getFilms = getResponse(GetAllFilmsQuery())
        return getFilms?.data?.allFilms
    }

    override suspend fun fetchOneFilm(id: String): GetFilmQuery.Film? {
        val getFilm = apolloClient.query(query = GetFilmQuery(id = Optional.presentIfNotNull(id))).execute()
        return getFilm.data?.film
    }

    override suspend fun fetchPeoples(): GetAllPeoplesQuery.AllPeople? {
        val getPeoples = getResponse(GetAllPeoplesQuery())
        return getPeoples?.data?.allPeople
    }

    override suspend fun fetchOnePerson(id: String): GetPersonQuery.Person? {
        val getPerson = apolloClient.query(query = GetPersonQuery(id = Optional.presentIfNotNull(id))).execute()
        return getPerson.data?.person
    }

    override suspend fun fetchPlanets(): GetAllPlanetsQuery.AllPlanets? {
        val getPlanets = getResponse(GetAllPlanetsQuery())
        return getPlanets?.data?.allPlanets
    }

    override suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet? {
        val getPlanet = apolloClient.query(query = GetPlanetQuery(id = Optional.presentIfNotNull(id))).execute()
        return getPlanet.data?.planet
    }

    override suspend fun fetchSpecies(): GetAllSpeciesQuery.AllSpecies? {
        val getSpecies = getResponse(GetAllSpeciesQuery())
        return getSpecies?.data?.allSpecies
    }

    override suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species? {
        val getSpecie = apolloClient.query(query = GetSpecieQuery(id = Optional.presentIfNotNull(id))).execute()
        return getSpecie.data?.species
    }

    override suspend fun fetchStarships(): GetAllStarshipsQuery.AllStarships? {
        val getStarships = getResponse(GetAllStarshipsQuery())
        return getStarships?.data?.allStarships
    }

    override suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship? {
        val getStarship = apolloClient.query(query = GetStarshipQuery(id = Optional.presentIfNotNull(id))).execute()
        return getStarship.data?.starship
    }

    override suspend fun fetchVehicles(): GetAllVehiclesQuery.AllVehicles? {
        val getVehicles = getResponse(GetAllVehiclesQuery())
        return getVehicles?.data?.allVehicles
    }

    override suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle? {
        val getVehicle = apolloClient.query(query = GetVehicleQuery(id = Optional.presentIfNotNull(id))).execute()
        return getVehicle.data?.vehicle
    }

    private suspend fun <D : Query.Data> getResponse(query: Query<D>): ApolloResponse<D>? {
        try {
            return apolloClient.query(query = query).execute()
        } catch (exc: Exception) {
            Log.e("exception", "This is a huge exc: $exc")
        }
        return null
    }
}
