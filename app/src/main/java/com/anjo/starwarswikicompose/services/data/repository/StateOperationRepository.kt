package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class StateOperationRepository @Inject constructor(
        private val dataStore: DataStoreOperations,
) {

    suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.readingBoardingState()
    }

    suspend fun saveNotificationEnabled(enabled: Boolean) {
        dataStore.saveNotificationEnabled(enabled)
    }

    fun readNotificationEnabled(): Flow<Boolean> {
        return dataStore.readNotificationEnabled()
    }
}