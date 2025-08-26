package com.anjo.starwarswikicompose.services.data.repository.swmodels

import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.ModelChunkCrossRef
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.UniversalChunk
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle

interface SwModelRepository {
    suspend fun insertChunk(chunk: UniversalChunk): String

    suspend fun insertChunkCrossRef(modelChunkCrossRef: ModelChunkCrossRef)

    suspend fun deleteChunkCrossRefByModelId(modelId: String)

    suspend fun deleteChunkCrossRefByChunkId(chunkId: String)

    fun getUniversalChunkByCategory(category: Category): List<UniversalChunk>

    fun getUniversalChunkById(id: String): UniversalChunk?

    fun deleteUniversalChunkById(universalChunkId: String)

    fun getPersonById(id: String): Person

    suspend fun insertPerson(person: Person): String

    suspend fun deletePersonById(id: String)

    fun getMovieById(id: String): Movie

    suspend fun insertMovie(movie: Movie): String

    suspend fun deleteMovieById(id: String)

    fun getPlanetById(id: String): Planet

    suspend fun insertPlanet(planet: Planet): String

    suspend fun deletePlanetById(id: String)

    fun getSpecieById(id: String): Specie

    suspend fun insertSpecie(specie: Specie): String

    suspend fun deleteSpecieById(id: String)

    fun getVehicleById(id: String): Vehicle

    suspend fun insertVehicle(vehicle: Vehicle): String

    suspend fun deleteVehicleById(id: String)

    fun getStarshipById(id: String): Starship

    suspend fun insertStarship(starship: Starship): String

    suspend fun deleteStarshipById(id: String)
}