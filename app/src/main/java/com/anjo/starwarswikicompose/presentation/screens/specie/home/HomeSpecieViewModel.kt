package com.anjo.starwarswikicompose.presentation.screens.specie.home

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
class HomeSpecieViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedSpecies = MutableStateFlow(SpecieState())
    val fetchedSpecies = _fetchedSpecies.asStateFlow()

    fun getSpecies() {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedSpecies.update {
                it.copy(
                        isLoading = true
                )
            }
            delay(3.seconds)
            fetchSpecies()
        }
    }

    fun fetchSpecies() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedSpecies.update { state ->
                val species = useCase.getAllSpeciesUseCase()
                state.copy(
                        species = species,
                        isLoading = false
                )
            }
        }

    data class SpecieState(
            val species: List<UniversalChunk> = emptyList(),
            val isLoading: Boolean = false,
    )
}