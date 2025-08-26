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
import com.anjo.starwarswikicompose.services.data.database.swmodels.SwModelsDao

class SwModelService(private val swModelsDao: SwModelsDao) : SwModelRepository {
    override suspend fun insertChunk(chunk: UniversalChunk): String {
        swModelsDao.insertChunk(chunk)
        return chunk.id
    }

    override suspend fun insertChunkCrossRef(modelChunkCrossRef: ModelChunkCrossRef) {
        swModelsDao.insertChunkCrossRef(modelChunkCrossRef)
    }

    override suspend fun deleteChunkCrossRefByModelId(modelId: String) {
        swModelsDao.deleteChunkCrossRefByModelId(modelId)
    }

    override suspend fun deleteChunkCrossRefByChunkId(chunkId: String) {
        swModelsDao.deleteChunkCrossRefByChunkId(chunkId)
    }

    override fun getUniversalChunkByCategory(category: Category): List<UniversalChunk> {
        return swModelsDao.getUniversalChunkByCategory(category)
    }

    override fun getUniversalChunkById(id: String): UniversalChunk? {
        return swModelsDao.getUniversalChunkById(id)
    }

    override fun deleteUniversalChunkById(universalChunkId: String) {
        swModelsDao.deleteUniversalChunkById(universalChunkId = universalChunkId)
    }

    override fun getPersonById(id: String): Person {
        return swModelsDao.getPersonById(id)
    }

    override suspend fun insertPerson(person: Person): String {
        val entity = person.entity
        swModelsDao.insertPerson(entity)
        return entity.id
    }

    override suspend fun deletePersonById(id: String) {
        swModelsDao.deletePersonById(id)
    }

    override fun getMovieById(id: String): Movie {
        return swModelsDao.getMovieById(id)
    }


    override suspend fun insertMovie(movie: Movie): String {
        val entity = movie.entity
        swModelsDao.insertMovie(entity)
        return entity.id
    }

    override suspend fun deleteMovieById(id: String) {
        swModelsDao.deleteMovieById(id)
    }

    override fun getPlanetById(id: String): Planet {
        return swModelsDao.getPlanetById(id)
    }

    override suspend fun insertPlanet(planet: Planet): String {
        val entity = planet.entity
        swModelsDao.insertPlanet(entity)
        return entity.id
    }

    override suspend fun deletePlanetById(id: String) {
        swModelsDao.deletePlanetById(id)
    }

    override fun getSpecieById(id: String): Specie {
        return swModelsDao.getSpecieById(id)
    }

    override suspend fun insertSpecie(specie: Specie): String {
        val entity = specie.entity
        swModelsDao.insertSpecie(entity)
        return entity.id
    }

    override suspend fun deleteSpecieById(id: String) {
        swModelsDao.deleteSpecieById(id)
    }

    override fun getVehicleById(id: String): Vehicle {
        return swModelsDao.getVehicleById(id)
    }

    override suspend fun insertVehicle(vehicle: Vehicle): String {
        val entity = vehicle.entity
        swModelsDao.insertVehicle(entity)
        return entity.id
    }

    override suspend fun deleteVehicleById(id: String) {
        swModelsDao.deleteVehicleById(id)
    }

    override fun getStarshipById(id: String): Starship {
        return swModelsDao.getStarshipById(id)
    }

    override suspend fun insertStarship(starship: Starship): String {
        val entity = starship.entity
        swModelsDao.insertStarship(entity)
        return entity.id
    }

    override suspend fun deleteStarshipById(id: String) {
        swModelsDao.deleteStarshipById(id)
    }
}