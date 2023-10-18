package com.anjo.starwarswikicompose.di

import android.content.Context
import com.anjo.starwarswikicompose.data.DataStoreOperationImpl
import com.anjo.starwarswikicompose.data.Repository
import com.anjo.starwarswikicompose.services.repository.DataStoreOperations
import com.anjo.starwarswikicompose.services.usecases.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideDataStoreOperations(
            @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationImpl(context = context)
    }
    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
                saveOnboardingUseCase = SaveOnboardingUseCase(repository),
                readOnboardingUseCase = ReadOnboardingUseCase(repository)
        )
    }
}