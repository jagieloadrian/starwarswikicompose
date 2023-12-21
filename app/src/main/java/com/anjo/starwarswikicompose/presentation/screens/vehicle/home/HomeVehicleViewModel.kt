package com.anjo.starwarswikicompose.presentation.screens.vehicle.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVehicleViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedVehicles = MutableStateFlow(VehicleState())
    val fetchedVehicles = _fetchedVehicles

    fun getVehicles() {
        viewModelScope.launch {
            _fetchedVehicles.update {
                it.copy(
                        isLoading = true
                )
            }
            fetchVehicles()
        }
    }

    fun fetchVehicles() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedVehicles.update { state ->
                val vehicles = useCase.getAllVehicleUseCase()
                state.copy(
                        vehicles = vehicles,
                        isLoading = false
                )
            }
        }

    data class VehicleState(
            val vehicles: List<UniversalChunk> = emptyList(),
            val isLoading: Boolean = false,
    )
}