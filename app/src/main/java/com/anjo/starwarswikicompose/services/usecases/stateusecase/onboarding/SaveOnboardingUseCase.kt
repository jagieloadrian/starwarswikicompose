package com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository

class SaveOnboardingUseCase(
        private val operationRepository: StateOperationRepository,
) {

    suspend operator fun invoke(completed: Boolean) {
        operationRepository.saveOnboardingState(completed)
    }
}