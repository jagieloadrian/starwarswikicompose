package com.anjo.starwarswikicompose.services.data.database.converters

import androidx.room.TypeConverter
import com.anjo.starwarswikicompose.domain.model.sw.Category

class CategoryConverter {

    @TypeConverter
    fun fromCategory(status: Category?): String? {
        return status?.name
    }

    @TypeConverter
    fun toCategory(statusName: String?): Category? {
        return statusName?.let { Category.valueOf(it) }
    }
}