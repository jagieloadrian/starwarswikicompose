package com.anjo.starwarswikicompose.presentation.screens.vehicle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.VehicleDetailState
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_VEHICLE_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
open class VehicleViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val removeUseCase: DeleteUseCases,
        private val savedStateHandle: SavedStateHandle,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _selectedVehicle = MutableStateFlow(VehicleDetailState())
    open val selectedVehicle = _selectedVehicle.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getVehicle() {
        viewModelScope.launch(ioDispatcher) {
            _selectedVehicle.update {
                it.copy(state = LOADING)
            }
            delay(1500)
            fetchVehicle()
        }
    }

    private suspend fun fetchVehicle() {
        val vehicleId = savedStateHandle.get<String>(DETAILS_VEHICLE_ARGUMENT_KEY)
        vehicleId?.let { id ->
            _selectedVehicle.update { _ ->
                val vehicle = useCase.getVehicleUseCase(id = id)
                if (vehicle != null) {
                    VehicleDetailState(vehicleDto = vehicle, state = SUCCESS)
                } else VehicleDetailState()
            }
            _images.update {
                val images = imageSliderUseCases.getImagesForObjectUseCase(id, VEHICLES)
                images.toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = VEHICLES)
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(vehicleId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.update {
                val anotherList = imageSliderUseCases.getImagesForObjectUseCase(vehicleId, VEHICLES)
                anotherList.toMutableList()
            }
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun removeVehicle(id: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.collect { images ->
                images.forEach { image ->
                    imageSliderUseCases.deleteImageFromRoomUseCase(image)
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            removeUseCase.deleteVehicleUseCase(id)
        }
    }
}