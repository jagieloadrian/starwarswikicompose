package com.anjo.starwarswikicompose.data

import com.anjo.starwarswikicompose.services.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
        private val dataStore: DataStoreOperations
) {
    suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.readingBoardingState()
    }
}