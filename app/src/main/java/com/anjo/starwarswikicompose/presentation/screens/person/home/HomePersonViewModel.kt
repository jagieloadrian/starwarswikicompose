package com.anjo.starwarswikicompose.presentation.screens.person.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePersonViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {

    private val _fetchedPeople = MutableStateFlow(PeopleState())
    val fetchedPeople = _fetchedPeople.asStateFlow()

    init {
        viewModelScope.launch {
            _fetchedPeople.update {
                it.copy(
                        isLoading = true
                )
            }
            fetchPeople()
        }
    }

    fun fetchPeople() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedPeople.update { peopleState ->
                val people = useCase.getAllPeopleUseCase()
                if (people != null) {
                    peopleState.copy(
                            people = people,
                            isLoading = false
                    )
                } else {
                    peopleState.copy(
                            isLoading = true
                    )
                }
            }
        }
}

data class PeopleState(
        val people: List<GetAllPeoplesQuery.Person?>? = emptyList(),
        val isLoading: Boolean = false
)
