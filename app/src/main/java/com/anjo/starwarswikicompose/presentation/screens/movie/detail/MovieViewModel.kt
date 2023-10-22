package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetFilmQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
        private val useCase: UseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedMovie: MutableStateFlow<GetFilmQuery.Film?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<GetFilmQuery.Film?> = _selectedMovie

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val movieId = savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY)
            _selectedMovie.value = movieId?.let { useCase.getMovieUseCase(id = it) }
        }
    }
}