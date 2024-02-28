package com.anjo.starwarswikicompose.presentation.screens.starship.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.StarshipsDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
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
open class StarshipViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedStarship = MutableStateFlow(StarshipsDetailState())
    open val selectedStarship = _selectedStarship.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images


    fun getStarship() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedStarship.update {
                it.copy(state = LOADING)
            }
            delay(2.seconds)
            fetchStarship()
        }
    }

    private fun fetchStarship() {
        viewModelScope.launch(Dispatchers.IO) {
            val starshipId = savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY)
            starshipId?.let {
                _selectedStarship.update { _ ->
                    val starship = useCase.getStarshipUseCase(id = it)
                    if (starship != null) {
                        StarshipsDetailState(starship = starship, state = SUCCESS)
                    } else StarshipsDetailState()
                }
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(it, STARSHIPS)
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = STARSHIPS)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(starshipId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(starshipId, STARSHIPS)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}