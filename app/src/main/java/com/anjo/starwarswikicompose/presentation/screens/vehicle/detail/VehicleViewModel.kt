package com.anjo.starwarswikicompose.presentation.screens.vehicle.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedVehicle: MutableStateFlow<GetVehicleQuery.Vehicle?> = MutableStateFlow(null)
    val selectedVehicle: StateFlow<GetVehicleQuery.Vehicle?> = _selectedVehicle

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val vehicleId = savedStateHandle.get<String>(Constants.DETAILS_VEHICLE_ARGUMENT_KEY)
            _selectedVehicle.value = vehicleId?.let { useCase.getVehicleUseCase(id = it) }
            vehicleId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(vehicleId, Category.VEHICLES)
            }
        }
    }

    fun saveInDatabase(selected: GetVehicleQuery.Vehicle, photoUrl: String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(movieId, Category.VEHICLES)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}