package com.anjo.starwarswikicompose.services.data.database.notes

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateConverterTest {

    @Test
    fun `given long when convert toLocalDateTime then return readable date`() {
        //given
        val converter = DateConverter()
        val epochDay: Long = 946725071
        val expected = LocalDateTime.of(2000, 1, 1, 11, 11,11)

        println(expected.toInstant(ZoneOffset.UTC))

        //when
        val actual = converter.run {
            epochDay.toLocalDateTime()
        }

        //then
        actual shouldBe expected
    }


    @Test
    fun `given localDateTime when convert toLong then return epochDay`() {
        //given
        val converter = DateConverter()
        val date = LocalDateTime.of(2000, 1, 1, 11, 11,11)
        val expected: Long = 946725071

        //when
        val actual = converter.run {
            date.toLong()
        }

        //then
        actual shouldBe expected
    }
}