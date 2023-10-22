package com.anjo.starwarswikicompose.presentation.screens.planet.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePlanetViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedPlanets = MutableStateFlow(PlanetState())
    val fetchedPlanets = _fetchedPlanets.asStateFlow()

    init {
        viewModelScope.launch {
            _fetchedPlanets.update {
                it.copy(
                        isLoading = true
                )
            }
            fetchPlanets()
        }
    }

    fun fetchPlanets() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedPlanets.update { state ->
                val planets = useCase.getAllPlanetsUseCase()
                if (planets != null) {
                    state.copy(
                            planets = planets,
                            isLoading = false
                    )
                } else {
                    state.copy(
                            isLoading = true
                    )
                }
            }
        }

    data class PlanetState(
            val planets: List<GetAllPlanetsQuery.Planet?>? = emptyList(),
            val isLoading: Boolean = false
    )
}