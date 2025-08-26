package com.anjo.starwarswikicompose.domain.model.sw

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.APOLLO
import com.anjo.starwarswikicompose.utils.Constants.MODEL_CHUNK_CROSS_REF_TABLE
import com.anjo.starwarswikicompose.utils.Constants.UNIVERSAL_CHUNK_TABLE
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

enum class SourceType {
    APOLLO, ROOM
}

@OptIn(ExperimentalUuidApi::class)
@Entity(tableName = UNIVERSAL_CHUNK_TABLE,
        indices = [
            Index(value = ["id"], unique = true)
        ])
data class UniversalChunk(
        @PrimaryKey
        val id: String = Uuid.random().toString(),
        val name: String = "",
        val desc: String = "",
        val category: Category = Category.ALL,
        val sourceType: SourceType = APOLLO,
)

@Entity(
        tableName = MODEL_CHUNK_CROSS_REF_TABLE,
        primaryKeys = ["modelId", "chunkId"],
        indices = [
            Index(value = ["modelId"]),
            Index(value = ["chunkId"])
        ])
data class ModelChunkCrossRef(
        val modelId: String = "",
        val chunkId: String = "",
)