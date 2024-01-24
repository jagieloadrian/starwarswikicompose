package com.anjo.starwarswikicompose.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WelcomeViewModel @Inject constructor(
        private val useCases: UseCases,
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.saveOnboardingUseCase(completed = completed)
        }
    }
}
