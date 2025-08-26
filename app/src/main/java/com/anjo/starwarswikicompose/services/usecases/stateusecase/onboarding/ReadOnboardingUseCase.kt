package com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
        private val operationRepository: StateOperationRepository,
) {

    operator fun invoke(): Flow<Boolean> {
        return operationRepository.readOnboardingState()
    }
}