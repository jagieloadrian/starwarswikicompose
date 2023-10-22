package com.anjo.starwarswikicompose.presentation.screens.starship.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStarshipViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedStarship = MutableStateFlow(StarshipState())
    val fetchedStarships = _fetchedStarship

    init {
        viewModelScope.launch {
            _fetchedStarship.update {
                it.copy(
                        isLoading = true
                )
            }
            fetchStarships()
        }
    }

    fun fetchStarships() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedStarship.update { state ->
                val starships = useCase.getAllStarshipsUseCase()
                if (starships != null) {
                    state.copy(
                            starships = starships,
                            isLoading = false
                    )
                } else {
                    state.copy(
                            isLoading = true
                    )
                }
            }
        }

    data class StarshipState(
            val starships: List<GetAllStarshipsQuery.Starship?>? = emptyList(),
            val isLoading: Boolean = false
    )
}