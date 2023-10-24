package com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding

import android.util.Log
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository

class SaveOnboardingUseCase(
        private val operationRepository: OperationRepository
) {

    suspend operator fun invoke(completed: Boolean) {
        Log.i(this.javaClass.simpleName, "SaveOnboardingUseCase: $completed")
        operationRepository.saveOnboardingState(completed)
    }
}