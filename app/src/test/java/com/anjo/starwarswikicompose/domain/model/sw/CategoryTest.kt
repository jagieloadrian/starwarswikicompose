package com.anjo.starwarswikicompose.domain.model.sw

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun `given property CategoryWithoutAllProperty should have less components than Category Enum`() {
        //when
        val actual = CategoryWithoutAllProperty.size
        //then
        actual shouldNotBe Category.entries.size

    }
}