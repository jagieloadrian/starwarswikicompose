package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedMovie: MutableStateFlow<Movie> = MutableStateFlow(Movie())
    val selectedMovie: StateFlow<Movie> = _selectedMovie

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    open val images: StateFlow<List<ImageSliderModel>> = _images

    fun getMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieId = savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY)
            _selectedMovie.value = movieId?.let { useCase.getMovieUseCase(id = it) } ?: Movie()
            movieId?.let {
                _images.value = imageSliderUseCases.getImagesForObjectUseCase(movieId, FILMS).toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = FILMS)
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