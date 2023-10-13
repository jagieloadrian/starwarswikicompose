package com.anjo.starwarswikicompose.services.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed:Boolean)

    fun readingBoardingState(): Flow<Boolean>
}