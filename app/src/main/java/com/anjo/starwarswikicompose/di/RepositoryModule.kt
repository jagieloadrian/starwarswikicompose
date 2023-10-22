package com.anjo.starwarswikicompose.di

import android.content.Context
import com.anjo.starwarswikicompose.data.DataStoreOperationImpl
import com.anjo.starwarswikicompose.data.Repository
import com.anjo.starwarswikicompose.services.repository.DataStoreOperations
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.services.usecases.images.GetRecentImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.images.GetSearchImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.movie.GetAllFilmsUseCase
import com.anjo.starwarswikicompose.services.usecases.movie.GetMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.onboarding.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.person.GetAllPeopleUseCase
import com.anjo.starwarswikicompose.services.usecases.person.GetPersonUseCase
import com.anjo.starwarswikicompose.services.usecases.planet.GetAllPlanetsUseCase
import com.anjo.starwarswikicompose.services.usecases.planet.GetPlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.specie.GetAllSpeciesUseCase
import com.anjo.starwarswikicompose.services.usecases.specie.GetSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.starship.GetAllStarshipsUseCase
import com.anjo.starwarswikicompose.services.usecases.starship.GetStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.vehicle.GetAllVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.vehicle.GetVehicleUseCase
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
                readOnboardingUseCase = ReadOnboardingUseCase(repository),
                getAllFilmsUseCase = GetAllFilmsUseCase(repository),
                getMovieUseCase = GetMovieUseCase(repository),
                getAllPeopleUseCase = GetAllPeopleUseCase(repository),
                getPersonUseCase = GetPersonUseCase(repository),
                getAllPlanetsUseCase = GetAllPlanetsUseCase(repository),
                getPlanetUseCase = GetPlanetUseCase(repository),
                getAllSpeciesUseCase = GetAllSpeciesUseCase(repository),
                getSpecieUseCase = GetSpecieUseCase(repository),
                getAllStarshipsUseCase = GetAllStarshipsUseCase(repository),
                getStarshipUseCase = GetStarshipUseCase(repository),
                getAllVehicleUseCase = GetAllVehicleUseCase(repository),
                getVehicleUseCase = GetVehicleUseCase(repository),
                getRecentImagesUseCase = GetRecentImagesUseCase(repository),
                getSearchImagesUseCase = GetSearchImagesUseCase(repository)
        )
    }
}