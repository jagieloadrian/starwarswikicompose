package com.anjo.starwarswikicompose.services.di

import android.content.Context
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperationImpl
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetRecentImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetSearchImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetAllFilmsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.person.GetAllPeopleUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.person.GetPersonUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.planet.GetAllPlanetsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.planet.GetPlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.specie.GetAllSpeciesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.specie.GetSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.starship.GetAllStarshipsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.starship.GetStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle.GetAllVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle.GetVehicleUseCase
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
            @ApplicationContext context: Context,
    ): DataStoreOperations {
        return DataStoreOperationImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(operationRepository: OperationRepository): UseCases {
        return UseCases(
                saveOnboardingUseCase = SaveOnboardingUseCase(operationRepository),
                readOnboardingUseCase = ReadOnboardingUseCase(operationRepository),
                getAllFilmsUseCase = GetAllFilmsUseCase(operationRepository),
                getMovieUseCase = GetMovieUseCase(operationRepository),
                getAllPeopleUseCase = GetAllPeopleUseCase(operationRepository),
                getPersonUseCase = GetPersonUseCase(operationRepository),
                getAllPlanetsUseCase = GetAllPlanetsUseCase(operationRepository),
                getPlanetUseCase = GetPlanetUseCase(operationRepository),
                getAllSpeciesUseCase = GetAllSpeciesUseCase(operationRepository),
                getSpecieUseCase = GetSpecieUseCase(operationRepository),
                getAllStarshipsUseCase = GetAllStarshipsUseCase(operationRepository),
                getStarshipUseCase = GetStarshipUseCase(operationRepository),
                getAllVehicleUseCase = GetAllVehicleUseCase(operationRepository),
                getVehicleUseCase = GetVehicleUseCase(operationRepository),
                getRecentImagesUseCase = GetRecentImagesUseCase(operationRepository),
                getSearchImagesUseCase = GetSearchImagesUseCase(operationRepository)
        )
    }
}