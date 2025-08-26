package com.anjo.starwarswikicompose.services.apollofetcher

import android.util.Log
import com.anjo.starwarswikicompose.apollo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.apollo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.apollo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.apollo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.apollo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.apollo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.apollo.GetFilmQuery
import com.anjo.starwarswikicompose.apollo.GetPersonQuery
import com.anjo.starwarswikicompose.apollo.GetPlanetQuery
import com.anjo.starwarswikicompose.apollo.GetSpecieQuery
import com.anjo.starwarswikicompose.apollo.GetStarshipQuery
import com.anjo.starwarswikicompose.apollo.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.mapper.mapFromFilms
import com.anjo.starwarswikicompose.domain.mapper.mapFromPersons
import com.anjo.starwarswikicompose.domain.mapper.mapFromPlanets
import com.anjo.starwarswikicompose.domain.mapper.mapFromSpecies
import com.anjo.starwarswikicompose.domain.mapper.mapFromStarships
import com.anjo.starwarswikicompose.domain.mapper.mapFromVehicles
import com.anjo.starwarswikicompose.domain.mapper.mapToMovie
import com.anjo.starwarswikicompose.domain.mapper.mapToPerson
import com.anjo.starwarswikicompose.domain.mapper.mapToPlanet
import com.anjo.starwarswikicompose.domain.mapper.mapToSpecie
import com.anjo.starwarswikicompose.domain.mapper.mapToStarship
import com.anjo.starwarswikicompose.domain.mapper.mapToVehicle
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.api.Query

class DataFetcherImpl(private val apolloClient: ApolloClient) : DataFetcher {

    override suspend fun fetchFilms(): List<UniversalChunkDto> {
        val response = getResponse(GetAllFilmsQuery(), Category.FILMS)?.data?.allFilms?.films ?: listOf()
        return response.mapFromFilms()
    }

    override suspend fun fetchOneFilm(id: String): MovieDto? {
        val query = GetFilmQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.FILMS)?.data?.film?.mapToMovie()
    }

    override suspend fun fetchPeoples(): List<UniversalChunkDto> {
        val response = getResponse(GetAllPeoplesQuery(), Category.PEOPLE)?.data?.allPeople?.people ?: listOf()
        return response.mapFromPersons()
    }

    override suspend fun fetchOnePerson(id: String): PersonDto? {
        val query = GetPersonQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.PEOPLE)?.data?.person?.mapToPerson()
    }

    override suspend fun fetchPlanets(): List<UniversalChunkDto> {
        val response = getResponse(GetAllPlanetsQuery(), Category.PLANETS)?.data?.allPlanets?.planets ?: listOf()
        return response.mapFromPlanets()
    }

    override suspend fun fetchOnePlanet(id: String): PlanetDto? {
        val query = GetPlanetQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.PLANETS)?.data?.planet?.mapToPlanet()
    }

    override suspend fun fetchSpecies(): List<UniversalChunkDto> {
        val response = getResponse(GetAllSpeciesQuery(), Category.SPECIES)?.data?.allSpecies?.species ?: listOf()
        return response.mapFromSpecies()
    }

    override suspend fun fetchOneSpecie(id: String): SpecieDto? {
        val query = GetSpecieQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.SPECIES)?.data?.species?.mapToSpecie()
    }

    override suspend fun fetchStarships(): List<UniversalChunkDto> {
        val response =
            getResponse(GetAllStarshipsQuery(), Category.STARSHIPS)?.data?.allStarships?.starships ?: listOf()
        return response.mapFromStarships()
    }

    override suspend fun fetchOneStarship(id: String): StarshipDto? {
        val query = GetStarshipQuery(id = Optional.presentIfNotNull(id))
        return getResponse(query, Category.STARSHIPS)?.data?.starship?.mapToStarship()
    }

    override suspend fun fetchVehicles(): List<UniversalChunkDto> {
        val response = getResponse(GetAllVehiclesQuery(), Category.VEHICLES)?.data?.allVehicles?.vehicles ?: listOf()
        return response.mapFromVehicles()
    }

    override suspend fun fetchOneVehicle(id: String): VehicleDto? {
        val query = GetVehicleQuery(Optional.presentIfNotNull(id))
        return getResponse(query, Category.VEHICLES)?.data?.vehicle?.mapToVehicle()
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
