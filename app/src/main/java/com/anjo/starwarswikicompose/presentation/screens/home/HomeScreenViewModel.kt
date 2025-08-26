package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.ALL
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.CategoryWithoutAllProperty
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
        private val useCase: UseCases,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _fetchedChunks = MutableStateFlow(ChunkState())
    val fetchedChunks = _fetchedChunks

    fun getChunks(category: Category = FILMS) {
        viewModelScope.launch(ioDispatcher) {
            _fetchedChunks.update {
                it.copy(isLoading = true)
            }
            delay(1.seconds)
            fetchChunks(category)
        }
    }

    private fun fetchChunks(category: Category) {
        viewModelScope.launch(ioDispatcher) {
            _fetchedChunks.update { state ->
                val chunks = fetchProperlyChunks(category)
                state.copy(
                        chunks = chunks,
                        isLoading = false
                )
            }
        }
    }

    private suspend fun fetchProperlyChunks(category: Category): List<UniversalChunkDto> {
        return when (category) {
            FILMS     -> useCase.getAllFilmsUseCase()
            ALL       -> fetchAllChunks()
            PEOPLE    -> useCase.getAllPeopleUseCase()
            PLANETS   -> useCase.getAllPlanetsUseCase()
            SPECIES   -> useCase.getAllSpeciesUseCase()
            STARSHIPS -> useCase.getAllStarshipsUseCase()
            VEHICLES  -> useCase.getAllVehicleUseCase()
        }
    }

    private suspend fun fetchAllChunks(): List<UniversalChunkDto> {
        return coroutineScope {
            val deferred = CategoryWithoutAllProperty.map {
                async {
                    fetchProperlyChunks(it)
                }
            }
            deferred.awaitAll()
        }.flatten()
    }

    data class ChunkState(
            val chunks: List<UniversalChunkDto> = emptyList(),
            val isLoading: Boolean = false,
    )
}