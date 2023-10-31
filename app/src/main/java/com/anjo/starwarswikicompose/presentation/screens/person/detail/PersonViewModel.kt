package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetPersonQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedPerson: MutableStateFlow<GetPersonQuery.Person?> = MutableStateFlow(null)
    val selectedPerson: StateFlow<GetPersonQuery.Person?> = _selectedPerson

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images:StateFlow<List<ImageSliderModel>> = _images

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val personId = savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY)
            _selectedPerson.value = personId?.let { useCase.getPersonUseCase(id = it) }
            personId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(personId, Category.PEOPLE)
            }
        }
    }

    fun saveInDatabase(selected: GetPersonQuery.Person, photoUrl: String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun refreshImages(personId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(personId, Category.PEOPLE)
            _images.value = anotherList
        }
    }
}