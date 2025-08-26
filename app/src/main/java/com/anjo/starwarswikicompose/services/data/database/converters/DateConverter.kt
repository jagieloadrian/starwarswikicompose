package com.anjo.starwarswikicompose.services.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateConverter {

    private val offset = ZoneOffset.UTC

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime {
        return value?.let {
            LocalDateTime.ofEpochSecond(it, 0, offset)
        } ?: LocalDateTime.now(offset)
    }

    @TypeConverter
    fun toLong(value: LocalDateTime?): Long {
        val currentTime = value ?: LocalDateTime.now()
        return currentTime.toEpochSecond(offset)
    }

}