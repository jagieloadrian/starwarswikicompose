package com.anjo.starwarswikicompose.di

import com.anjo.starwarswikicompose.domain.usecases.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.domain.usecases.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.domain.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(): UseCases {
        return UseCases(
                saveOnboardingUseCase = SaveOnboardingUseCase(),
                readOnboardingUseCase = ReadOnboardingUseCase()
        )
    }
}