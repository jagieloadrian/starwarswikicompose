package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcherImpl
import com.anjo.starwarswikicompose.utils.createAllFilmsEmptyObject
import com.anjo.starwarswikicompose.utils.createAllPeopleEmptyObject
import com.anjo.starwarswikicompose.utils.createAllPlanetsEmptyObject
import com.anjo.starwarswikicompose.utils.createAllSpeciesEmptyObject
import com.anjo.starwarswikicompose.utils.createAllStarshipsEmptyObject
import com.anjo.starwarswikicompose.utils.createAllVehiclesEmptyObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataFetcherImpl: DataFetcherImpl) : ViewModel() {

    private val _fetchedFilms = MutableStateFlow(createAllFilmsEmptyObject())
    val fetchedFilms = _fetchedFilms
    private val _fetchedPeople = MutableStateFlow(createAllPeopleEmptyObject())
    val fetchedPeoples = _fetchedPeople
    private val _fetchedPlanet = MutableStateFlow(createAllPlanetsEmptyObject())
    val fetchedPlanets = _fetchedPlanet
    private val _fetchedSpecie = MutableStateFlow(createAllSpeciesEmptyObject())
    val fetchedSpecies = _fetchedSpecie
    private val _fetchedStarship = MutableStateFlow(createAllStarshipsEmptyObject())
    val fetchedStarships = _fetchedStarship
    private val _fetchedVehicles = MutableStateFlow(createAllVehiclesEmptyObject())
    val fetchedVehicles = _fetchedVehicles

    fun fetchFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchFilms()?.let { _fetchedFilms.value = it }
        }
    }

    fun fetchPeoples() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchPeoples()?.let { _fetchedPeople.value = it }
        }
    }

    fun fetchPlanets() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchPlanets()?.let { _fetchedPlanet.value = it }
        }
    }

    fun fetchSpecies() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchSpecies()?.let { _fetchedSpecie.value = it }
        }
    }

    fun fetchStarships() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchStarships()?.let { _fetchedStarship.value = it }
        }
    }

    fun fetchVehicles() {
        viewModelScope.launch(Dispatchers.IO) {
            dataFetcherImpl.fetchVehicles()?.let { _fetchedVehicles.value = it }
        }
    }
}