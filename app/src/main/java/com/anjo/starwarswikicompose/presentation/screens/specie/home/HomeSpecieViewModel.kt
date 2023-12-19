package com.anjo.starwarswikicompose.presentation.screens.specie.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSpecieViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedSpecies = MutableStateFlow(SpecieState())
    val fetchedSpecies = _fetchedSpecies.asStateFlow()

    init {
        viewModelScope.launch {
            _fetchedSpecies.update {
                it.copy(
                        isLoading = true
                )
            }
            fetchSpecies()
        }
    }

    fun fetchSpecies() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedSpecies.update { state ->
                val species = useCase.getAllSpeciesUseCase()
                if (species != null) {
                    state.copy(
                            species = species,
                            isLoading = false
                    )
                } else {
                    state.copy(
                            isLoading = true
                    )
                }
            }
        }

    data class SpecieState(
            val species: List<GetAllSpeciesQuery.Species?>? = emptyList(),
            val isLoading: Boolean = false
    )
}