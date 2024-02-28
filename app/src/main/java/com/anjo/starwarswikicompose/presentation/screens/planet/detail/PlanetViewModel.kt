package com.anjo.starwarswikicompose.presentation.screens.planet.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.PlanetDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
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
open class PlanetViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedPlanet = MutableStateFlow(PlanetDetailState())
    open val selectedPlanet = _selectedPlanet.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getPlanet() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedPlanet.update {
                it.copy(state = LOADING)
            }
            delay(2.seconds)
            fetchPlanet()
        }
    }

    private fun fetchPlanet() {
        viewModelScope.launch(Dispatchers.IO) {
            val planetId = savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY)
            planetId?.let {
                _selectedPlanet.update { _ ->
                    val planet = useCase.getPlanetUseCase(id = it)
                    if (planet != null) {
                        PlanetDetailState(planet = planet, state = SUCCESS)
                    } else PlanetDetailState()
                }
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