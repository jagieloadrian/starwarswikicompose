package com.anjo.starwarswikicompose.presentation.screens.planet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.PlanetDetailState
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
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
open class PlanetViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val removeUseCase: DeleteUseCases,
        private val savedStateHandle: SavedStateHandle,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _selectedPlanet = MutableStateFlow(PlanetDetailState())
    open val selectedPlanet = _selectedPlanet.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getPlanet() {
        viewModelScope.launch(ioDispatcher) {
            _selectedPlanet.update {
                it.copy(state = LOADING)
            }
            delay(1500)
            fetchPlanet()
        }
    }

    private suspend fun fetchPlanet() {
        val planetId = savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY)
        planetId?.let { id ->
            _selectedPlanet.update { _ ->
                val planet = useCase.getPlanetUseCase(id = id)
                if (planet != null) {
                    PlanetDetailState(planetDto = planet, state = SUCCESS)
                } else PlanetDetailState()
            }
            _images.update {
                val images = imageSliderUseCases.getImagesForObjectUseCase(id, PLANETS)
                images.toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = PLANETS)
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(planetId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.update {
                val anotherList = imageSliderUseCases.getImagesForObjectUseCase(planetId, PLANETS)
                anotherList.toMutableList()
            }
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun removePlanet(id: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.collect { images ->
                images.forEach { image ->
                    imageSliderUseCases.deleteImageFromRoomUseCase(image)
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            removeUseCase.deletePlanetUseCase(id)
        }
    }
}