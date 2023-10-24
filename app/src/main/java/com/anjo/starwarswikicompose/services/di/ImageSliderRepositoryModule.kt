package com.anjo.starwarswikicompose.services.di

import android.content.Context
import androidx.room.Room
import com.anjo.starwarswikicompose.services.data.database.ImageSliderDao
import com.anjo.starwarswikicompose.services.data.database.ImageSliderDb
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderService
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.AddImageToRoomUseCase
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.DeleteImageFromRoomUseCase
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.GetImagesForObjectUseCase
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.utils.Constants.IMAGE_SLIDER_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageSliderRepositoryModule {

    @Provides
    @Singleton
    fun provideImageSliderDb(
            @ApplicationContext
            context: Context,
    ) = Room.databaseBuilder(
            context,
            ImageSliderDb::class.java,
            IMAGE_SLIDER_TABLE
    ).build()

    @Provides
    @Singleton
    fun provideImageSliderDao(
            imageSliderDb: ImageSliderDb,
    ) = imageSliderDb.imageSliderDao

    @Provides
    @Singleton
    fun provideImageSliderRepository(
            imageSliderDao: ImageSliderDao,
    ): ImageSliderRepository {
        return ImageSliderService(
                imageSliderDao = imageSliderDao
        )
    }

    @Provides
    @Singleton
    fun provideImageSliderUseCases(repository: ImageSliderRepository): ImageSliderUseCases {
        return ImageSliderUseCases(
                getImagesForObjectUseCase = GetImagesForObjectUseCase(repository),
                addImageToRoomUseCase = AddImageToRoomUseCase(repository),
                deleteImageFromRoomUseCase = DeleteImageFromRoomUseCase(repository)
        )
    }
}