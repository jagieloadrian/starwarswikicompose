package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Category.FILMS
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.toImageSliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedMovie: MutableStateFlow<GetFilmQuery.Film?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<GetFilmQuery.Film?> = _selectedMovie

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val movieId = savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY)
            _selectedMovie.value = movieId?.let { useCase.getMovieUseCase(id = it) }
            movieId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(movieId, FILMS).toMutableList()
            }
        }
    }

    fun saveInDatabase(selected: GetFilmQuery.Film, photoUrl: String) {
        val modelObject = selected.toImageSliderModel(photoUrl)
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun refreshImages(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val anotherList = imageSliderUseCases.getImagesForObjectUseCase(movieId, FILMS)
            _images.value = anotherList
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(Dispatchers.IO) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}