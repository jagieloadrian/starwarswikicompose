package com.anjo.starwarswikicompose.presentation.screens.starship

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.StarshipsDetailState
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
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
open class StarshipViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val removeUseCase: DeleteUseCases,
        private val savedStateHandle: SavedStateHandle,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _selectedStarship = MutableStateFlow(StarshipsDetailState())
    open val selectedStarship = _selectedStarship.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images


    fun getStarship() {
        viewModelScope.launch(ioDispatcher) {
            _selectedStarship.update {
                it.copy(state = LOADING)
            }
            delay(1500)
            fetchStarship()
        }
    }

    private suspend fun fetchStarship() {
        val starshipId = savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY)
        starshipId?.let { id ->
            _selectedStarship.update { _ ->
                val starship = useCase.getStarshipUseCase(id = id)
                if (starship != null) {
                    StarshipsDetailState(starshipDto = starship, state = SUCCESS)
                } else StarshipsDetailState()
            }
            _images.update {
                val images = imageSliderUseCases.getImagesForObjectUseCase(id, STARSHIPS)
                images.toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = STARSHIPS)
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(starshipId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.update {
                val anotherList = imageSliderUseCases.getImagesForObjectUseCase(starshipId, STARSHIPS)
                anotherList.toMutableList()
            }
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun removeStarship(id: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.collect { images ->
                images.forEach { image ->
                    imageSliderUseCases.deleteImageFromRoomUseCase(image)
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            removeUseCase.deleteStarshipUseCase(id)
        }
    }
}