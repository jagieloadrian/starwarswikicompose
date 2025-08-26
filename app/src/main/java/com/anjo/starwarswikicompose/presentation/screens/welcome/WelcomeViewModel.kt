package com.anjo.starwarswikicompose.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.services.usecases.stateusecase.StateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
open class WelcomeViewModel @Inject constructor(
        private val useCases: StateUseCase,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            useCases.saveOnboardingUseCase(completed = completed)
        }
    }
}
