package com.anjo.starwarswikicompose.services.usecases

import com.anjo.starwarswikicompose.data.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
        private val repository: Repository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.readOnboardingState()
    }
}