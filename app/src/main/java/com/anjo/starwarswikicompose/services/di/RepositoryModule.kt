package com.anjo.starwarswikicompose.services.di

import android.content.Context
import androidx.room.Room
import com.anjo.starwarswikicompose.services.data.database.swmodels.SwModelsDao
import com.anjo.starwarswikicompose.services.data.database.swmodels.SwModelsDb
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import com.anjo.starwarswikicompose.services.data.repository.PhotoOperationRepository
import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperationImpl
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import com.anjo.starwarswikicompose.services.data.repository.swmodels.SwModelRepository
import com.anjo.starwarswikicompose.services.data.repository.swmodels.SwModelService
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeletePersonUseCase
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeletePlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertChunkUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertPersonUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertPlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetRecentImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetSearchImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetAllFilmsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetMovieUseCase
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
import com.anjo.starwarswikicompose.services.usecases.stateusecase.StateUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.notification.ReadNotificationsEnabledUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.notification.SaveNotificationsEnabledUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.utils.Constants.SW_MODEL_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSwModelDb(
            @ApplicationContext
            context: Context,
    ) = Room.databaseBuilder(
            context,
            SwModelsDb::class.java,
            SW_MODEL_DB).build()

    @Provides
    fun provideSwModelDao(
            swModelsDb: SwModelsDb,
    ) = swModelsDb.swModelsDao

    @Provides
    fun provideSwModelRepository(
            swModelsDao: SwModelsDao
    ): SwModelRepository {
        return SwModelService(swModelsDao)
    }

    @Provides
    fun provideDataStoreOperations(
            @ApplicationContext context: Context,
    ): DataStoreOperations {
        return DataStoreOperationImpl(context = context)
    }

    @Provides
    fun provideUseCases(operationRepository: OperationRepository,
                        photoOperationRepository: PhotoOperationRepository): UseCases {
        return UseCases(
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
                getRecentImagesUseCase = GetRecentImagesUseCase(photoOperationRepository),
                getSearchImagesUseCase = GetSearchImagesUseCase(photoOperationRepository)
        )
    }

    @Provides
    fun provideStateUseCase(stateOperationRepository: StateOperationRepository): StateUseCase {
        return StateUseCase(
                saveOnboardingUseCase = SaveOnboardingUseCase(stateOperationRepository),
                readOnboardingUseCase = ReadOnboardingUseCase(stateOperationRepository),
                readNotificationEnabled = ReadNotificationsEnabledUseCase(stateOperationRepository),
                saveNotificationEnabled = SaveNotificationsEnabledUseCase(stateOperationRepository)
        )
    }

    @Provides
    fun provideInsertUseCases(operationRepository: OperationRepository): InsertUseCases {
        return InsertUseCases(
                insertMovieUseCase = InsertMovieUseCase(operationRepository),
                insertChunkUseCase = InsertChunkUseCase(operationRepository),
                insertPersonUseCase = InsertPersonUseCase(operationRepository),
                insertPlanetUseCase = InsertPlanetUseCase(operationRepository),
                insertSpecieUseCase = InsertSpecieUseCase(operationRepository),
                insertStarshipUseCase = InsertStarshipUseCase(operationRepository),
                insertVehicleUseCase = InsertVehicleUseCase(operationRepository))
    }

    @Provides
    fun provideRemoveUseCase(operationRepository: OperationRepository): DeleteUseCases {
        return DeleteUseCases(
                deleteMovieUseCase = DeleteMovieUseCase(operationRepository),
                deletePersonUseCase = DeletePersonUseCase(operationRepository),
                deleteStarshipUseCase = DeleteStarshipUseCase(operationRepository),
                deleteVehicleUseCase = DeleteVehicleUseCase(operationRepository),
                deleteSpecieUseCase = DeleteSpecieUseCase(operationRepository),
                deletePlanetUseCase = DeletePlanetUseCase(operationRepository))
    }

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}