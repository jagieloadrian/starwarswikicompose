package com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
        private val operationRepository: OperationRepository,
) {

    operator fun invoke(): Flow<Boolean> {
        return operationRepository.readOnboardingState()
    }
}