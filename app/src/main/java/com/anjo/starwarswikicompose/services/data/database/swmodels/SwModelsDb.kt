package com.anjo.starwarswikicompose.services.data.database.swmodels

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anjo.starwarswikicompose.domain.model.sw.ModelChunkCrossRef
import com.anjo.starwarswikicompose.domain.model.sw.MovieEntity
import com.anjo.starwarswikicompose.domain.model.sw.PersonEntity
import com.anjo.starwarswikicompose.domain.model.sw.PlanetEntity
import com.anjo.starwarswikicompose.domain.model.sw.SpecieEntity
import com.anjo.starwarswikicompose.domain.model.sw.StarshipEntity
import com.anjo.starwarswikicompose.domain.model.sw.UniversalChunk
import com.anjo.starwarswikicompose.domain.model.sw.VehicleEntity
import com.anjo.starwarswikicompose.services.data.database.converters.ArrayStringConverter
import com.anjo.starwarswikicompose.services.data.database.converters.CategoryConverter
import com.anjo.starwarswikicompose.services.data.database.converters.DateConverter

@Database(
        entities = [MovieEntity::class, PersonEntity::class, PlanetEntity::class, SpecieEntity::class,
            VehicleEntity::class, StarshipEntity::class, UniversalChunk::class, ModelChunkCrossRef::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(value = [ArrayStringConverter::class, DateConverter::class, CategoryConverter::class])
abstract class SwModelsDb : RoomDatabase() {
    abstract val swModelsDao: SwModelsDao
}