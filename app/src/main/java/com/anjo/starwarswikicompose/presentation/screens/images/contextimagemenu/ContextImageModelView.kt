package com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContextImageModelView @Inject constructor(
        private val useCases: UseCases,
        private val imagesUseCase: ImageSliderUseCases
) : ViewModel() {

    val name = mutableStateOf("")
    private val _number = MutableStateFlow(0)
    val number: StateFlow<Int> = _number.asStateFlow()
    fun findName(id: String, category: Category) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            name.value = findNameByUseCase(id, category) ?: "\uD83D\uDE4A"
        }
    }

    fun saveInDatabase(modelObject: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imagesUseCase.addImageToRoomUseCase(modelObject)
        }
    }

    private suspend fun findNameByUseCase(id: String, category: Category): String? {
        return when (category) {
            Category.FILMS     -> useCases.getMovieUseCase(id)?.title
            Category.PEOPLE    -> useCases.getPersonUseCase(id)?.name
            Category.PLANETS   -> useCases.getPlanetUseCase(id)?.name
            Category.SPECIES   -> useCases.getSpecieUseCase(id)?.name
            Category.STARSHIPS -> useCases.getStarshipUseCase(id)?.name
            Category.VEHICLES  -> useCases.getVehicleUseCase(id)?.name
        }
    }
}