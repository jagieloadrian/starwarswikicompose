package com.anjo.starwarswikicompose.domain.model.imageslider

import androidx.compose.runtime.StableMarker
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.utils.Constants.IMAGE_SLIDER_TABLE

@Entity(tableName = IMAGE_SLIDER_TABLE, indices = [Index(value = ["objectId", "url"], unique = true)])
@StableMarker
data class ImageSliderModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val objectId: String = "",
        val url: String = "",
        val objectType: Category = Category.FILMS,
)
