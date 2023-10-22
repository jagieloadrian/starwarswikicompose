package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.GetPersonQuery
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
        private val useCase: UseCases,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _selectedPerson: MutableStateFlow<GetPersonQuery.Person?> = MutableStateFlow(null)
    val selectedPerson: StateFlow<GetPersonQuery.Person?> = _selectedPerson

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val personId = savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY)
            _selectedPerson.value = personId?.let { useCase.getPersonUseCase(id = it) }
        }
    }
}