package com.anjo.starwarswikicompose.services.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel

@Database(
        entities = [ImageSliderModel::class],
        version = 1,
        exportSchema = false
)
abstract class ImageSliderDb : RoomDatabase() {
    abstract val imageSliderDao: ImageSliderDao
}