package com.anjo.starwarswikicompose.presentation.screens.planet.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PlanetViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedPlanet: MutableStateFlow<Planet> = MutableStateFlow(Planet())
    open val selectedPlanet: StateFlow<Planet> = _selectedPlanet

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getPlanet() {
        viewModelScope.launch(Dispatchers.IO) {
            val planetId = savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY)
            _selectedPlanet.value = planetId?.let { useCase.getPlanetUseCase(id = it) } ?: Planet()
            planetId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(planetId, Category.PLANETS)
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = Category.PLANETS)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(planetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(planetId, Category.PLANETS)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}