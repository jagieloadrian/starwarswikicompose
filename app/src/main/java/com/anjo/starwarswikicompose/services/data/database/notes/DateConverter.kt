package com.anjo.starwarswikicompose.services.data.database.notes

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateConverter {

    private val offset = ZoneOffset.UTC

    @TypeConverter
    fun Long.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofEpochSecond(this, 0, offset)
    }

    @TypeConverter
    fun LocalDateTime.toLong(): Long {
        return this.toEpochSecond(offset)
    }

}