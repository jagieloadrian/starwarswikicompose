package com.anjo.starwarswikicompose.services.data.database.converters

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ArrayStringConverterTest {

    @Test
    fun `given null when convert fromList then return empty list json `() {
        //given
        val expected = emptyList<String>().toString()
        //when
        val actual = ArrayStringConverter().fromList(null)
        //then
        actual shouldBe expected
    }

    @Test
    fun `given listOfString when convert fromList then return empty list json `() {
        //given
        val expected = listOf("Adam", "Kamil", "Lucyna")
        //when
        val actual = ArrayStringConverter().fromList(expected)
        //then
        actual shouldBe "[${expected.joinToString(",") { "\"$it\"" }}]"
    }

    @Test
    fun `given emptyList when convert fromList then return empty list json `() {
        //given
        val expected = emptyList<String>()
        //when
        val actual = ArrayStringConverter().fromList(expected)
        //then
        actual shouldBe expected.toString()
    }

    @Test
    fun `given null when convert fromString then return empty list json `() {
        //given
        val expected = emptyList<String>()
        //when
        val actual = ArrayStringConverter().fromString(null)
        //then
        actual shouldBe expected
    }

    @Test
    fun `given listOfString when convert fromString then return empty list json `() {
        //given
        val expected = listOf("Adam", "Kamil", "Lucyna")
        //when
        val actual = ArrayStringConverter().fromString("""["Adam","Kamil","Lucyna"]""")
        //then
        actual shouldBe expected
    }

    @Test
    fun `given emptyList when convert fromString then return empty list json `() {
        //given
        val expected = emptyList<String>()
        //when
        val actual = ArrayStringConverter().fromString("[]")
        //then
        actual shouldBe expected
    }

}