package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.SpecieDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
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
open class SpecieViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedSpecie = MutableStateFlow(SpecieDetailState())
    open val selectedSpecie = _selectedSpecie.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getSpecie() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedSpecie.update {
                it.copy(state = LOADING)
            }
            delay(2.seconds)
            fetchSpecie()
        }
    }

    private fun fetchSpecie() {
        viewModelScope.launch(Dispatchers.IO) {
            val specieId = savedStateHandle.get<String>(DETAILS_SPECIE_ARGUMENT_KEY)
            specieId?.let {
                _selectedSpecie.update { _ ->
                    val specie = useCase.getSpecieUseCase(id = it)
                    if (specie != null) {
                        SpecieDetailState(specie = specie, state = SUCCESS)
                    } else SpecieDetailState()
                }
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(it, Category.SPECIES).toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = Category.SPECIES)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(specieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(specieId, Category.SPECIES)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}