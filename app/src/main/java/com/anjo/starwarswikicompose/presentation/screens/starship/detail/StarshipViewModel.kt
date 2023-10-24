package com.anjo.starwarswikicompose.presentation.screens.starship.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetStarshipQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarshipViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedStarship: MutableStateFlow<GetStarshipQuery.Starship?> = MutableStateFlow(null)
    val selectedStarship: StateFlow<GetStarshipQuery.Starship?> = _selectedStarship

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val starshipId = savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY)
            _selectedStarship.value = starshipId?.let { useCase.getStarshipUseCase(id = it) }
            starshipId?.let {
                imageSliderUseCases.getImagesForObjectUseCase(starshipId, STARSHIPS)
                        .collect {
                            _images.value = it
                        }
            }
        }
    }

    fun saveInDatabase(selected: GetStarshipQuery.Starship, photoUrl:String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }
}