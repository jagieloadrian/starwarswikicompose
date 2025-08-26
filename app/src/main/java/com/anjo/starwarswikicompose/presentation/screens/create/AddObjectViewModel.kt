package com.anjo.starwarswikicompose.presentation.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.ALL
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddObjectViewModel @Inject constructor(
        private val useCase: UseCases,
        private val insertUseCase: InsertUseCases,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _currentObjectId = MutableStateFlow("")
    val currentObjectId = _currentObjectId.asStateFlow()

    private val _movieChunks = MutableStateFlow(ChunkState())
    val movieChunks = _movieChunks.asStateFlow()

    private val _charChunks = MutableStateFlow(ChunkState())
    val charChunks = _charChunks.asStateFlow()

    private val _planetChunks = MutableStateFlow(ChunkState())
    val planetChunks = _planetChunks.asStateFlow()

    private val _vehicleChunks = MutableStateFlow(ChunkState())
    val vehicleChunks = _vehicleChunks.asStateFlow()

    private val _starshipChunks = MutableStateFlow(ChunkState())
    val starshipChunks = _starshipChunks.asStateFlow()

    private val _specieChunks = MutableStateFlow(ChunkState())
    val specieChunks = _specieChunks.asStateFlow()

    fun getChunksByCategory(category: Category) {
        viewModelScope.launch(ioDispatcher) {
            val chunks = fetchProperlyChunks(category)
            val state = ChunkState(chunks)
            updateProperlyProperty(category, state)
        }
    }

    fun cleanCurrentObjectId() {
        _currentObjectId.update {
            ""
        }
    }

    fun insertMovie(movie: MovieDto) {
        viewModelScope.launch(ioDispatcher) {
            val movieId = insertUseCase.insertMovieUseCase(movie)
            val chunk = movie.toChunkDto(movieId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, movieId)
        }
    }

    fun insertPerson(person: PersonDto) {
        viewModelScope.launch(ioDispatcher) {
            val personId = insertUseCase.insertPersonUseCase(person)
            val chunk = person.toChunkDto(personId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, personId)
        }
    }

    fun insertPlanet(planet: PlanetDto) {
        viewModelScope.launch(ioDispatcher) {
            val planetId = insertUseCase.insertPlanetUseCase(planet)
            val chunk = planet.toChunkDto(planetId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, planetId)
        }
    }

    fun insertSpecie(specie: SpecieDto) {
        viewModelScope.launch(ioDispatcher) {
            val specieId = insertUseCase.insertSpecieUseCase(specie)
            val chunk = specie.toChunkDto(specieId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, specieId)
        }
    }

    fun insertStarship(starship: StarshipDto) {
        viewModelScope.launch(ioDispatcher) {
            val starshipId = insertUseCase.insertStarshipUseCase(starship)
            val chunk = starship.toChunkDto(starshipId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, starshipId)
        }
    }

    fun insertVehicle(vehicle: VehicleDto) {
        viewModelScope.launch(ioDispatcher) {
            val vehicleId = insertUseCase.insertVehicleUseCase(vehicle)
            val chunk = vehicle.toChunkDto(vehicleId, SourceType.ROOM)
            insertChunkAndUpdateId(chunk, vehicleId)
        }
    }

    private suspend fun insertChunkAndUpdateId(chunkDto: UniversalChunkDto, id: String) {
        insertUseCase.insertChunkUseCase(chunkDto)
        _currentObjectId.update {
            id
        }
    }

    private fun updateProperlyProperty(category: Category, state: ChunkState) {
        when (category) {
            FILMS     -> {
                _movieChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }

            ALL       -> throw IllegalArgumentException()
            PEOPLE    -> {
                _charChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }

            PLANETS   -> {
                _planetChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }

            SPECIES   -> {
                _specieChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }

            STARSHIPS -> {
                _starshipChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }

            VEHICLES  -> {
                _vehicleChunks.update { stateModel ->
                    stateModel.copy(chunks = state.chunks)
                }
            }
        }
    }

    private suspend fun fetchProperlyChunks(category: Category): List<UniversalChunkDto> {
        return when (category) {
            FILMS     -> useCase.getAllFilmsUseCase()
            ALL       -> throw IllegalArgumentException("Illegal category")
            PEOPLE    -> useCase.getAllPeopleUseCase()
            PLANETS   -> useCase.getAllPlanetsUseCase()
            SPECIES   -> useCase.getAllSpeciesUseCase()
            STARSHIPS -> useCase.getAllStarshipsUseCase()
            VEHICLES  -> useCase.getAllVehicleUseCase()
        }
    }

    data class ChunkState(
            val chunks: List<UniversalChunkDto> = emptyList(),
    )
}