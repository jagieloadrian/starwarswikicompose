package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.UniversalChunk

fun UniversalChunkDto.toModel(): UniversalChunk {
    return UniversalChunk(
            id = id,
            name = name,
            desc = desc,
            category = category,
            sourceType = sourceType
    )
}

fun UniversalChunk.toDtoByRoom(): UniversalChunkDto {
    return UniversalChunkDto(
            id = id,
            name = name,
            desc = desc,
            category = category,
            sourceType = sourceType
    )
}

fun List<UniversalChunk>.toConnectionDto(category: Category): ConnectionDto {
    val mappedChunks = filter { it.category == category }
            .map { it.toDtoByRoom() }
    return ConnectionDto(
            totalCount = mappedChunks.size,
            objects = mappedChunks
    )
}