package com.anjo.starwarswikicompose.presentation.screens.vehicle.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.VehicleDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
open class VehicleViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedVehicle = MutableStateFlow(VehicleDetailState())
    open val selectedVehicle = _selectedVehicle.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getVehicle() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedVehicle.update {
                it.copy(state = LOADING)
            }
            delay(2.seconds)
            fetchVehicle()
        }
    }

    private fun fetchVehicle() {
        viewModelScope.launch(Dispatchers.IO) {
            val vehicleId = savedStateHandle.get<String>(Constants.DETAILS_VEHICLE_ARGUMENT_KEY)
            vehicleId?.let {
                _selectedVehicle.update { _ ->
                    val vehicle = useCase.getVehicleUseCase(id = it)
                    if (vehicle != null) {
                        VehicleDetailState(vehicle = vehicle, state = SUCCESS)
                    } else VehicleDetailState()
                }
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(it, VEHICLES)
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = VEHICLES)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(vehicleId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(vehicleId, VEHICLES)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}