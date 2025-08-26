package com.anjo.starwarswikicompose.services.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayStringConverter {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val localString = value ?: return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(localString, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        val localList = list ?: emptyList()
        return Gson().toJson(localList) as String
    }
}