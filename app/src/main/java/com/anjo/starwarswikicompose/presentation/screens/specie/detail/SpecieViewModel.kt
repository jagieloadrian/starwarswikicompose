package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecieViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedSpecie: MutableStateFlow<GetSpecieQuery.Species?> = MutableStateFlow(null)
    val selectedSpecie: StateFlow<GetSpecieQuery.Species?> = _selectedSpecie

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val specieId = savedStateHandle.get<String>(DETAILS_SPECIE_ARGUMENT_KEY)
            _selectedSpecie.value = specieId?.let { useCase.getSpecieUseCase(id = it) }
            specieId?.let {
                imageSliderUseCases.getImagesForObjectUseCase(specieId, SPECIES)
                        .collect {
                            _images.value = it
                        }
            }
        }
    }

    fun saveInDatabase(selected: GetSpecieQuery.Species, photoUrl:String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }
}