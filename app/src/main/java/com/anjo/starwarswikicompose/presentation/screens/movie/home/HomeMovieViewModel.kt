package com.anjo.starwarswikicompose.presentation.screens.movie.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeMovieViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedFilms = MutableStateFlow(MovieState())
    val fetchedFilms = _fetchedFilms.asStateFlow()

    fun getMovies() {
        viewModelScope.launch {
            _fetchedFilms.update {
                it.copy(
                        isLoading = true
                )
            }
            delay(5.seconds)
            fetchFilms()
        }
    }

    fun fetchFilms() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedFilms.update { movieState ->
                val films = useCase.getAllFilmsUseCase().sortedBy { it.desc }
                movieState.copy(
                        movies = films,
                        isLoading = false
                )
            }
        }

    data class MovieState(
            val movies: List<UniversalChunk> = emptyList(),
            val isLoading: Boolean = false,
    )
}