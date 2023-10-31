package com.anjo.starwarswikicompose.presentation.screens.planet.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetPlanetQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedPlanet: MutableStateFlow<GetPlanetQuery.Planet?> = MutableStateFlow(null)
    val selectedPlanet: StateFlow<GetPlanetQuery.Planet?> = _selectedPlanet

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val planetId = savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY)
            _selectedPlanet.value = planetId?.let { useCase.getPlanetUseCase(id = it) }
            planetId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(planetId, Category.PLANETS)
            }
        }
    }

    fun saveInDatabase(selected: GetPlanetQuery.Planet, photoUrl: String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
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