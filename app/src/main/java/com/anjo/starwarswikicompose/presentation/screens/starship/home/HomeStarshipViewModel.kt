package com.anjo.starwarswikicompose.presentation.screens.starship.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeStarshipViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedStarship = MutableStateFlow(StarshipState())
    val fetchedStarships = _fetchedStarship

    fun getStarships() {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedStarship.update {
                it.copy(
                        isLoading = true
                )
            }
            delay(3.seconds)
            fetchStarships()
        }
    }

    fun fetchStarships() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedStarship.update { state ->
                val starships = useCase.getAllStarshipsUseCase()
                state.copy(
                        starships = starships,
                        isLoading = false
                )
            }
        }

    data class StarshipState(
            val starships: List<UniversalChunk> = emptyList(),
            val isLoading: Boolean = false,
    )
}