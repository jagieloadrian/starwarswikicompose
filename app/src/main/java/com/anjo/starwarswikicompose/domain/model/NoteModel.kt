package com.anjo.starwarswikicompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anjo.starwarswikicompose.utils.Constants.NOTES_TABLE
import java.time.LocalDateTime

@Entity(tableName = NOTES_TABLE)
data class NoteModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val text: String = "",
        val lastChanged: LocalDateTime = LocalDateTime.now(),
)