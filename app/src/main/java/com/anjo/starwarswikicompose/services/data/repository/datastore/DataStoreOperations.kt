package com.anjo.starwarswikicompose.services.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readingBoardingState(): Flow<Boolean>
}