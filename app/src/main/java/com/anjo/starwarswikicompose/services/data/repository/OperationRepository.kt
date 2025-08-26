@file:OptIn(ExperimentalUuidApi::class)

package com.anjo.starwarswikicompose.services.data.repository

import androidx.room.Transaction
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.mapper.toDto
import com.anjo.starwarswikicompose.domain.mapper.toDtoByRoom
import com.anjo.starwarswikicompose.domain.mapper.toModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.ModelChunkCrossRef
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.APOLLO
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.ROOM
import com.anjo.starwarswikicompose.domain.model.sw.UniversalChunk
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcher
import com.anjo.starwarswikicompose.services.data.repository.swmodels.SwModelRepository
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.uuid.ExperimentalUuidApi

class OperationRepository @Inject constructor(
        private val dataFetcher: DataFetcher,
        private val swModelRepository: SwModelRepository
) {
    suspend fun fetchFilms(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchFilms() }
            val room = async { swModelRepository.getUniversalChunkByCategory(FILMS) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOneFilm(id: String): MovieDto? {
        return if (isUuid(id)) swModelRepository.getMovieById(id).toDto()
        else dataFetcher.fetchOneFilm(id)
    }

    suspend fun fetchPeoples(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchPeoples() }
            val room = async { swModelRepository.getUniversalChunkByCategory(PEOPLE) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOnePerson(id: String): PersonDto? {
        return if (isUuid(id)) swModelRepository.getPersonById(id).toDto()
        else dataFetcher.fetchOnePerson(id)
    }

    suspend fun fetchPlanets(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchPlanets() }
            val room = async { swModelRepository.getUniversalChunkByCategory(PLANETS) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOnePlanet(id: String): PlanetDto? {
        return if (isUuid(id)) swModelRepository.getPlanetById(id).toDto()
        else dataFetcher.fetchOnePlanet(id)
    }

    suspend fun fetchSpecies(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchSpecies() }
            val room = async { swModelRepository.getUniversalChunkByCategory(SPECIES) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOneSpecie(id: String): SpecieDto? {
        return if (isUuid(id)) swModelRepository.getSpecieById(id).toDto()
        else dataFetcher.fetchOneSpecie(id)
    }

    suspend fun fetchStarships(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchStarships() }
            val room = async { swModelRepository.getUniversalChunkByCategory(STARSHIPS) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOneStarship(id: String): StarshipDto? {
        return if (isUuid(id)) swModelRepository.getStarshipById(id).toDto()
        else dataFetcher.fetchOneStarship(id)
    }

    suspend fun fetchVehicles(): List<UniversalChunkDto> {
        return coroutineScope {
            val apollo = async { dataFetcher.fetchVehicles() }
            val room = async { swModelRepository.getUniversalChunkByCategory(VEHICLES) }
            val apolloChunks = apollo.await()
            val roomChunks = room.await()
            mergeChunks(apolloChunks, roomChunks)
        }
    }

    suspend fun fetchOneVehicle(id: String): VehicleDto? {
        return if (isUuid(id)) swModelRepository.getVehicleById(id).toDto()
        else dataFetcher.fetchOneVehicle(id)
    }

    private fun mergeChunks(apolloChunks: List<UniversalChunkDto>,
                            roomChunks: List<UniversalChunk>): List<UniversalChunkDto> {
        return apolloChunks + roomChunks.map { it.toDtoByRoom() }
    }

    @Transaction
    suspend fun insertMovie(movieDto: MovieDto): String {
        val mainModel = movieDto.toModel()
        val modelId = swModelRepository.insertMovie(mainModel)

        saveRelatedEntities(mainModel.planetChunks, modelId = modelId)
        saveRelatedEntities(mainModel.vehicleChunks, modelId = modelId)
        saveRelatedEntities(mainModel.starshipChunks, modelId = modelId)
        saveRelatedEntities(mainModel.specieChunks, modelId = modelId)
        saveRelatedEntities(mainModel.characterChunks, modelId = modelId)

        return modelId
    }

    @Transaction
    suspend fun insertPerson(personDto: PersonDto): String {
        val specieId = insertChunk(personDto.specie.toModel())
        val homeworldId = insertChunk(personDto.homeworld.toModel())

        val mainModel = personDto.toModel(homeworldId, specieId)
        val modelId = swModelRepository.insertPerson(mainModel)

        mainModel.movieChunks.forEach { movie ->
            saveRelatedChunk(movie, modelId)
        }

        mainModel.starshipChunks.forEach { starship ->
            saveRelatedChunk(starship, modelId)
        }

        mainModel.vehicleChunks.forEach { vehicle ->
            saveRelatedChunk(vehicle, modelId)
        }
        return modelId
    }

    @Transaction
    suspend fun insertSpecie(specieDto: SpecieDto): String {
        val homeworldId = insertChunk(specieDto.homeworld.toModel())

        val mainModel = specieDto.toModel(homeworldId)
        val modelId = swModelRepository.insertSpecie(mainModel)

        mainModel.movieChunks.forEach { movie ->
            saveRelatedChunk(movie, modelId)
        }
        mainModel.characterChunks.forEach { character ->
            saveRelatedChunk(character, modelId)
        }
        return modelId
    }

    @Transaction
    suspend fun insertPlanet(planetDto: PlanetDto): String {
        val mainModel = planetDto.toModel()
        val modelId = swModelRepository.insertPlanet(mainModel)

        mainModel.movieChunks.forEach { movie ->
            saveRelatedChunk(movie, modelId)
        }
        mainModel.characterChunks.forEach { character ->
            saveRelatedChunk(character, modelId)
        }
        return modelId
    }

    @Transaction
    suspend fun insertVehicle(vehicleDto: VehicleDto): String {
        val mainModel = vehicleDto.toModel()
        val modelId = swModelRepository.insertVehicle(mainModel)

        mainModel.movieChunks.forEach { movie ->
            saveRelatedChunk(movie, modelId)
        }
        mainModel.characterChunks.forEach { character ->
            saveRelatedChunk(character, modelId)
        }
        return modelId
    }

    @Transaction
    suspend fun insertStarship(starshipDto: StarshipDto): String {
        val mainModel = starshipDto.toModel()
        val modelId = swModelRepository.insertStarship(mainModel)

        mainModel.movieChunks.forEach { movie ->
            saveRelatedChunk(movie, modelId)
        }
        mainModel.characterChunks.forEach { character ->
            saveRelatedChunk(character, modelId)
        }
        return modelId
    }

    suspend fun removeMovie(movieId: String) {
        fetchOneFilm(movieId) ?: throw IllegalArgumentException("Movie with id: $movieId not found")
        removeReferences(movieId)
        swModelRepository.deleteUniversalChunkById(movieId)
        swModelRepository.deleteMovieById(movieId)
    }

    suspend fun removePerson(personId: String) {
        fetchOnePerson(personId) ?: throw IllegalArgumentException("Person with id: $personId not found")
        removeReferences(personId)
        swModelRepository.deleteUniversalChunkById(personId)
        swModelRepository.deletePersonById(personId)
    }

    suspend fun removeSpecie(specieId: String) {
        fetchOneSpecie(specieId) ?: throw IllegalArgumentException("Specie with id: $specieId not found")
        removeReferences(specieId)
        swModelRepository.deleteUniversalChunkById(specieId)
        swModelRepository.deleteSpecieById(specieId)
    }

    suspend fun removePlanet(planetId: String) {
        fetchOnePlanet(planetId) ?: throw IllegalArgumentException("Planet with id: $planetId not found")
        removeReferences(planetId)
        swModelRepository.deleteUniversalChunkById(planetId)
        swModelRepository.deletePlanetById(planetId)
    }

    suspend fun removeVehicle(vehicleId: String) {
        fetchOneVehicle(vehicleId) ?: throw IllegalArgumentException("Vehicle with id: $vehicleId not found")
        removeReferences(vehicleId)
        swModelRepository.deleteUniversalChunkById(vehicleId)
        swModelRepository.deleteVehicleById(vehicleId)
    }

    suspend fun removeStarship(starshipId: String) {
        fetchOneStarship(starshipId) ?: throw IllegalArgumentException("Starship with id: $starshipId not found")
        removeReferences(starshipId)
        swModelRepository.deleteUniversalChunkById(starshipId)
        swModelRepository.deleteStarshipById(starshipId)
    }

    private suspend fun removeReferences(objectToRemoveId: String) {
        swModelRepository.deleteChunkCrossRefByModelId(objectToRemoveId)
        swModelRepository.deleteChunkCrossRefByChunkId(objectToRemoveId)
    }

    private suspend fun saveRelatedChunk(chunk: UniversalChunk, modelId: String) {
        val chunkId = insertChunk(chunk)
        val crossRef = ModelChunkCrossRef(modelId, chunkId)
        swModelRepository.insertChunkCrossRef(crossRef)
    }

    suspend fun insertChunk(chunkModel: UniversalChunk): String {
        return when (chunkModel.sourceType) {
            APOLLO -> swModelRepository.getUniversalChunkById(chunkModel.id)?.id
            ROOM   -> swModelRepository.getUniversalChunkById(chunkModel.id)?.id
        } ?: swModelRepository.insertChunk(chunkModel)
    }

    private suspend fun saveRelatedEntities(chunks: List<UniversalChunk>, modelId: String) {
        chunks.forEach { chunk ->
            val chunkId = insertChunk(chunk)
            swModelRepository.insertChunkCrossRef(ModelChunkCrossRef(modelId, chunkId))
        }
    }

    private fun isUuid(id: String): Boolean {
        return id.isNotBlank() && id.length == 36 || id.length == 32
    }
}
