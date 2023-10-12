package com.anjo.starwarswikicompose.presentation.screens.details.specie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcherImpl
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecieViewModel @Inject constructor(
        private val dataFetcherImpl: DataFetcherImpl,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedSpecie: MutableStateFlow<GetSpecieQuery.Species?> = MutableStateFlow(null)
    val selectedSpecie: StateFlow<GetSpecieQuery.Species?> = _selectedSpecie

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val specieId = savedStateHandle.get<String>(DETAILS_SPECIE_ARGUMENT_KEY)
            _selectedSpecie.value = specieId?.let { dataFetcherImpl.fetchOneSpecie(id = it) }
        }
    }
}