package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.PersonDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
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
open class PersonViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedPerson = MutableStateFlow(PersonDetailState())
    open val selectedPerson = _selectedPerson.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getPerson() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedPerson.update {
                it.copy(state = LOADING)
            }
            delay(2.seconds)
            fetchPerson()
        }
    }

    fun fetchPerson() {
        viewModelScope.launch(Dispatchers.IO) {
            val personId = savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY)
            personId?.let {
                _selectedPerson.update { _ ->
                    val person = useCase.getPersonUseCase(id = it)
                    if (person != null) {
                        PersonDetailState(person = person, state = SUCCESS)
                    } else PersonDetailState()
                }
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(it, PEOPLE)
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = PEOPLE)

        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun refreshImages(personId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(personId, PEOPLE)
            _images.value = anotherList
        }
    }
}