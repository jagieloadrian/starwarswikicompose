package com.anjo.starwarswikicompose.presentation.screens.planet.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetPlanetQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
        private val useCase: UseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedPlanet: MutableStateFlow<GetPlanetQuery.Planet?> = MutableStateFlow(null)
    val selectedPlanet: StateFlow<GetPlanetQuery.Planet?> = _selectedPlanet

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val planetId = savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY)
            _selectedPlanet.value = planetId?.let { useCase.getPlanetUseCase(id = it) }
        }
    }
}