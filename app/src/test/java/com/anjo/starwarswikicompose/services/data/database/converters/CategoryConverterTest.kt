package com.anjo.starwarswikicompose.services.data.database.converters

import com.anjo.starwarswikicompose.domain.model.sw.Category
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class CategoryConverterTest {

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `given category when convert fromCategory then return properly string`(category: Category) {
        //when
        val actual = CategoryConverter().fromCategory(category)

        //then
        actual shouldBe category.name
    }

    @Test
    fun `given null when convert fromCategory then return null`() {
        //when
        val actual = CategoryConverter().fromCategory(null)

        //then
        actual shouldBe null
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `given category when convert toCategory then return properly category`(category: Category) {
        //when
        val actual = CategoryConverter().toCategory(category.name)

        //then
        actual shouldBe category
    }

    @Test
    fun `given null when convert toCategory then return null`() {
        //when
        val actual = CategoryConverter().toCategory(null)

        //then
        actual shouldBe null
    }
}