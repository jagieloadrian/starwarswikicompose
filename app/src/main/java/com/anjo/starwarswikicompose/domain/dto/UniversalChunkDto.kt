package com.anjo.starwarswikicompose.domain.dto

import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.APOLLO

data class UniversalChunkDto(
        val id: String = "",
        val name: String = "",
        val desc: String = "",
        val category: Category,
        val sourceType: SourceType = APOLLO
)

data class ConnectionDto(
        val totalCount: Int,
        val objects: List<UniversalChunkDto>,
)

fun emptyConnection(): ConnectionDto = ConnectionDto(0, listOf())