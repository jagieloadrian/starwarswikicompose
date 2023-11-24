package com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository

class SaveOnboardingUseCase(
        private val operationRepository: OperationRepository,
) {

    suspend operator fun invoke(completed: Boolean) {
        operationRepository.saveOnboardingState(completed)
    }
}