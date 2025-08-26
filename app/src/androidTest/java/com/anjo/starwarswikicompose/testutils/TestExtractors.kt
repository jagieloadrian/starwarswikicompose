package com.anjo.starwarswikicompose.testutils

import com.anjo.starwarswikicompose.domain.dto.ConnectionDto

fun extractNames(connection: ConnectionDto): List<String> {
    return connection.objects.subList(0, 2)
            .map { it.name }.toList()
}