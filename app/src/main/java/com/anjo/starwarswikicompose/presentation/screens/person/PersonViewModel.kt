package com.anjo.starwarswikicompose.presentation.screens.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.PersonDetailState
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
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
open class PersonViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val removeUseCase: DeleteUseCases,
        private val savedStateHandle: SavedStateHandle,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _selectedPerson = MutableStateFlow(PersonDetailState())
    val selectedPerson = _selectedPerson.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    fun getPerson() {
        viewModelScope.launch(ioDispatcher) {
            _selectedPerson.update {
                it.copy(state = LOADING)
            }
            delay(1500)
            fetchPerson()
        }
    }

    private suspend fun fetchPerson() {
        val personId = savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY)
        personId?.let { id ->
            _selectedPerson.update { _ ->
                val person = useCase.getPersonUseCase(id = id)
                if (person != null) {
                    PersonDetailState(personDto = person, state = SUCCESS)
                } else PersonDetailState()
            }
            _images.update {
                val images = imageSliderUseCases.getImagesForObjectUseCase(id, PEOPLE)
                images.toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = PEOPLE)

        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }

    fun refreshImages(personId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.update {
                val anotherList = imageSliderUseCases.getImagesForObjectUseCase(personId, PEOPLE)
                anotherList.toMutableList()
            }
        }
    }

    fun removePerson(personId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.collect { images ->
                images.forEach { image ->
                    imageSliderUseCases.deleteImageFromRoomUseCase(image)
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            removeUseCase.deletePersonUseCase(personId)
        }
    }
}