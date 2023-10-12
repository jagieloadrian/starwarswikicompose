package com.anjo.starwarswikicompose.presentation.screens.details.vehicle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetVehicleQuery
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcherImpl
import com.anjo.starwarswikicompose.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
        private val dataFetcherImpl: DataFetcherImpl,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedVehicle: MutableStateFlow<GetVehicleQuery.Vehicle?> = MutableStateFlow(null)
    val selectedVehicle: StateFlow<GetVehicleQuery.Vehicle?> = _selectedVehicle

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val planetId = savedStateHandle.get<String>(Constants.DETAILS_VEHICLE_ARGUMENT_KEY)
            _selectedVehicle.value = planetId?.let { dataFetcherImpl.fetchOneVehicle(id = it) }
        }
    }
}