package com.anjo.starwarswikicompose.services.apollofetcher

import android.util.Log
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
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.mapper.mapFromFilms
import com.anjo.starwarswikicompose.services.mapper.mapFromPersons
import com.anjo.starwarswikicompose.services.mapper.mapFromPlanets
import com.anjo.starwarswikicompose.services.mapper.mapFromSpecies
import com.anjo.starwarswikicompose.services.mapper.mapFromStarships
import com.anjo.starwarswikicompose.services.mapper.mapFromVehicles
import com.anjo.starwarswikicompose.services.mapper.mapToMovie
import com.anjo.starwarswikicompose.services.mapper.mapToPerson
import com.anjo.starwarswikicompose.services.mapper.mapToPlanet
import com.anjo.starwarswikicompose.services.mapper.mapToSpecie
import com.anjo.starwarswikicompose.services.mapper.mapToStarship
import com.anjo.starwarswikicompose.services.mapper.mapToVehicle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query

class DataFetcherImpl(private val apolloClient: ApolloClient) : DataFetcher {

    override suspend fun fetchFilms(): List<UniversalChunk> {
        val response = getResponse(GetAllFilmsQuery(), Category.FILMS)?.data?.allFilms?.films ?: listOf()
        return response.mapFromFilms()
    }

    override suspend fun fetchOneFilm(id: String): Movie {
        val query = GetFilmQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.FILMS)?.data?.film?.mapToMovie() ?: Movie()
    }

    override suspend fun fetchPeoples(): List<UniversalChunk> {
        val response = getResponse(GetAllPeoplesQuery(), Category.PEOPLE)?.data?.allPeople?.people ?: listOf()
        return response.mapFromPersons()
    }

    override suspend fun fetchOnePerson(id: String): Person {
        val query = GetPersonQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.PEOPLE)?.data?.person?.mapToPerson() ?: Person()
    }

    override suspend fun fetchPlanets(): List<UniversalChunk> {
        val response = getResponse(GetAllPlanetsQuery(), Category.PLANETS)?.data?.allPlanets?.planets ?: listOf()
        return response.mapFromPlanets()
    }

    override suspend fun fetchOnePlanet(id: String): Planet {
        val query = GetPlanetQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.PLANETS)?.data?.planet?.mapToPlanet() ?: Planet()
    }

    override suspend fun fetchSpecies(): List<UniversalChunk> {
        val response = getResponse(GetAllSpeciesQuery(), Category.SPECIES)?.data?.allSpecies?.species ?: listOf()
        return response.mapFromSpecies()
    }

    override suspend fun fetchOneSpecie(id: String): Specie {
        val query = GetSpecieQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.SPECIES)?.data?.species?.mapToSpecie() ?: Specie()
    }

    override suspend fun fetchStarships(): List<UniversalChunk> {
        val response =
            getResponse(GetAllStarshipsQuery(), Category.STARSHIPS)?.data?.allStarships?.starships ?: listOf()
        return response.mapFromStarships()
    }

    override suspend fun fetchOneStarship(id: String): Starship {
        val query = GetStarshipQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.STARSHIPS)?.data?.starship?.mapToStarship() ?: Starship()
    }

    override suspend fun fetchVehicles(): List<UniversalChunk> {
        val response = getResponse(GetAllVehiclesQuery(), Category.VEHICLES)?.data?.allVehicles?.vehicles ?: listOf()
        return response.mapFromVehicles()
    }

    override suspend fun fetchOneVehicle(id: String): Vehicle {
        val query = GetVehicleQuery(Optional.presentIfNotNull(id))
        return getResponse(query, Category.VEHICLES)?.data?.vehicle?.mapToVehicle() ?: Vehicle()
    }

    private suspend fun <D : Query.Data> getResponse(query: Query<D>, enum: Category): ApolloResponse<D>? {
        try {
            return apolloClient.query(query = query).execute()
        } catch (exc: Exception) {
            Log.e("exception", "$exc for ${enum.name}")
        }
        return null
    }
}
