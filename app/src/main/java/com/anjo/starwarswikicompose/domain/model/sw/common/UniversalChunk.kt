package com.anjo.starwarswikicompose.domain.model.sw.common

data class UniversalChunk(
        val id: String = "",
        val name: String = "",
        val desc: String = "",
)

data class Connection(
        val totalCount: Int,
        val objects: List<UniversalChunk>,
)
