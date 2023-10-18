package com.anjo.starwarswikicompose.presentation.screens.details.starship

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetStarshipQuery
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcherImpl
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarshipViewModel @Inject constructor(
        private val dataFetcherImpl: DataFetcherImpl,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedStarship: MutableStateFlow<GetStarshipQuery.Starship?> = MutableStateFlow(null)
    val selectedStarship: StateFlow<GetStarshipQuery.Starship?> = _selectedStarship

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val starshipId = savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY)
            _selectedStarship.value = starshipId?.let { dataFetcherImpl.fetchOneStarship(id = it) }
        }
    }
}