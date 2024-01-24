package com.anjo.starwarswikicompose.presentation.screens.planet.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomePlanetViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedPlanets = MutableStateFlow(PlanetState())
    val fetchedPlanets = _fetchedPlanets.asStateFlow()

    fun getPlanets() {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedPlanets.update {
                it.copy(
                        isLoading = true
                )
            }
            delay(3.seconds)
            fetchPlanets()
        }
    }

    fun fetchPlanets() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedPlanets.update { state ->
                val planets = useCase.getAllPlanetsUseCase()
                state.copy(
                        planets = planets,
                        isLoading = false
                )
            }
        }

    data class PlanetState(
            val planets: List<UniversalChunk> = emptyList(),
            val isLoading: Boolean = false,
    )
}