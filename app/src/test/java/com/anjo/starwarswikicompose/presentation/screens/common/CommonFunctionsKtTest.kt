package com.anjo.starwarswikicompose.presentation.screens.common

import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.common.findImage
import com.anjo.starwarswikicompose.presentation.common.shouldInstanceLazyRow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource

class CommonFunctionsKtTest {

    @ParameterizedTest
    @EnumSource(value = Category::class)
    fun `given id and category when findImage then return path`(category: Category) {
        //given
        val id = "objectId"
        val expected = "file:///android_asset/${category.categoryName.lowercase()}/objectId.jpg"

        //when
        val actual = findImage(id, category)

        //then
        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("numbers")
    fun `given numbers when shouldInstanceLazyRow then return boolean`(number: Int, expected: Boolean) {
        //when
        val actual = shouldInstanceLazyRow(number)

        //then
        actual shouldBe expected

    }

    companion object {
        @JvmStatic
        fun numbers(): List<Arguments> {
            return listOf(
                    Arguments.of(3, true),
                    Arguments.of(2, true),
                    Arguments.of(1, true),
                    Arguments.of(0, false),
                    Arguments.of(-1, false),
                    Arguments.of(-2, false),
                    Arguments.of(-3, false)
            )
        }
    }
}