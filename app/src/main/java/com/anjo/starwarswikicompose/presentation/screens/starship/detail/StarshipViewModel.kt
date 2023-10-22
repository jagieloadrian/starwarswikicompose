package com.anjo.starwarswikicompose.presentation.screens.starship.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetStarshipQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarshipViewModel @Inject constructor(
        private val useCase: UseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedStarship: MutableStateFlow<GetStarshipQuery.Starship?> = MutableStateFlow(null)
    val selectedStarship: StateFlow<GetStarshipQuery.Starship?> = _selectedStarship

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val starshipId = savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY)
            _selectedStarship.value = starshipId?.let { useCase.getStarshipUseCase(id = it) }
        }
    }
}