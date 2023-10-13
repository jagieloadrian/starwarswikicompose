package com.anjo.starwarswikicompose.services.usecases

import android.util.Log
import com.anjo.starwarswikicompose.data.Repository

class SaveOnboardingUseCase(
        private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        Log.i(this.javaClass.simpleName, "SaveOnboardingUseCase: $completed")
        repository.saveOnboardingState(completed)
    }
}