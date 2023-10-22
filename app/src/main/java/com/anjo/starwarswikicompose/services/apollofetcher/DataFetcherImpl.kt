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

class DataFetcherImpl(private val apolloClient: ApolloClient) : DataFetcher {
    override suspend fun fetchFilms(): List<GetAllFilmsQuery.Film?>? {
        return getResponse(GetAllFilmsQuery(), Category.FILMS)?.data?.allFilms?.films
    }

    override suspend fun fetchOneFilm(id: String): GetFilmQuery.Film? {
        val query = GetFilmQuery(id = Optional.presentIfNotNull(id))
        val getFilm = getResponse(query, Category.FILMS)
        return getFilm?.data?.film
    }

    override suspend fun fetchPeoples(): List<GetAllPeoplesQuery.Person?>? {
        return getResponse(GetAllPeoplesQuery(), Category.PEOPLE)?.data?.allPeople?.people
    }

    override suspend fun fetchOnePerson(id: String): GetPersonQuery.Person? {
        val query = GetPersonQuery(id = Optional.presentIfNotNull(id))
        val getPerson = getResponse(query, Category.PEOPLE)
        return getPerson?.data?.person
    }

    override suspend fun fetchPlanets(): List<GetAllPlanetsQuery.Planet?>? {
        val getPlanets = getResponse(GetAllPlanetsQuery(), Category.PLANETS)
        return getPlanets?.data?.allPlanets?.planets
    }

    override suspend fun fetchOnePlanet(id: String): GetPlanetQuery.Planet? {
        val query = GetPlanetQuery(id = Optional.presentIfNotNull(id))
        val getPlanet = getResponse(query, Category.PLANETS)
        return getPlanet?.data?.planet
    }

    override suspend fun fetchSpecies(): List<GetAllSpeciesQuery.Species?>? {
        val getSpecies = getResponse(GetAllSpeciesQuery(), Category.SPECIES)
        return getSpecies?.data?.allSpecies?.species
    }

    override suspend fun fetchOneSpecie(id: String): GetSpecieQuery.Species? {
        val query = GetSpecieQuery(id = Optional.presentIfNotNull(id))
        val getSpecie = getResponse(query, Category.SPECIES)
        return getSpecie?.data?.species
    }

    override suspend fun fetchStarships(): List<GetAllStarshipsQuery.Starship?>? {
        val getStarships = getResponse(GetAllStarshipsQuery(), Category.STARSHIPS)
        return getStarships?.data?.allStarships?.starships
    }

    override suspend fun fetchOneStarship(id: String): GetStarshipQuery.Starship? {
        val query = GetStarshipQuery(id = Optional.presentIfNotNull(id))
        val getStarship = getResponse(query, Category.STARSHIPS)
        return getStarship?.data?.starship
    }

    override suspend fun fetchVehicles(): List<GetAllVehiclesQuery.Vehicle?>? {
        val getVehicles = getResponse(GetAllVehiclesQuery(), Category.VEHICLES)
        return getVehicles?.data?.allVehicles?.vehicles
    }

    override suspend fun fetchOneVehicle(id: String): GetVehicleQuery.Vehicle? {
        val query = GetVehicleQuery(Optional.presentIfNotNull(id))
        val getVehicle = getResponse(query, Category.VEHICLES)
        return getVehicle?.data?.vehicle
    }

    private suspend fun <D : Query.Data> getResponse(query: Query<D>, enum: Category): ApolloResponse<D>? {
        try {
            Log.e("Query", "Query for ${enum.name}")
            return apolloClient.query(query = query).execute()
        } catch (exc: Exception) {
            Log.e("exception", "$exc for ${enum.name}")
        }
        return null
    }
}
