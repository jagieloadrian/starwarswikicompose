package com.anjo.starwarswikicompose.domain.usecases

import android.util.Log

class SaveOnboardingUseCase {

    operator fun invoke(completed: Boolean) {
        Log.i(this.javaClass.simpleName, "SaveOnboardingUseCase: $completed")
    }
}