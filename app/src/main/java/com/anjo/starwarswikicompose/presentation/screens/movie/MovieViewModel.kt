package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.dto.MovieDetailState
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
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
open class MovieViewModel @Inject constructor(
        private val useCase: UseCases,
        private val imageSliderUseCases: ImageSliderUseCases,
        private val removeUseCase: DeleteUseCases,
        private val savedStateHandle: SavedStateHandle,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _selectedMovie = MutableStateFlow(MovieDetailState())
    val selectedMovie = _selectedMovie.asStateFlow()

    private var _images = MutableStateFlow(emptyList<ImageSliderModel>())
    val images: StateFlow<List<ImageSliderModel>> = _images

    fun getMovie() {
        viewModelScope.launch(ioDispatcher) {
            _selectedMovie.update {
                it.copy(state = LOADING)
            }
            delay(1500)
            fetchMovie()
        }
    }

    private suspend fun fetchMovie() {
        val movieId = savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY)
        movieId?.let { id ->
            _selectedMovie.update { _ ->
                val movie = useCase.getMovieUseCase(id = id)
                if (movie != null) {
                    MovieDetailState(movieDto = movie, state = SUCCESS)
                } else MovieDetailState()
            }
            _images.update {
                val images = imageSliderUseCases.getImagesForObjectUseCase(id, FILMS)
                images.toMutableList()
            }
        }
    }

    fun saveInDatabase(objectId: String, photoUrl: String) {
        val modelObject = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = FILMS)
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.addImageToRoomUseCase(modelObject)
        }
    }

    fun removeMovie(movieId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.collect { images ->
                images.forEach { image ->
                    imageSliderUseCases.deleteImageFromRoomUseCase(image)
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            removeUseCase.deleteMovieUseCase(movieId)
        }
    }

    fun refreshImages(movieId: String) {
        viewModelScope.launch(ioDispatcher) {
            _images.update {
                val anotherList = imageSliderUseCases.getImagesForObjectUseCase(movieId, FILMS)
                anotherList.toMutableList()
            }
        }
    }

    fun deleteFromDatabase(image: ImageSliderModel) {
        viewModelScope.launch(ioDispatcher) {
            imageSliderUseCases.deleteImageFromRoomUseCase(image)
        }
    }
}