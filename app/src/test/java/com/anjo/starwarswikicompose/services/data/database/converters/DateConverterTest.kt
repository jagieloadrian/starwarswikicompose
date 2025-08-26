package com.anjo.starwarswikicompose.services.data.database.converters

import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class DateConverterTest {

    @Test
    fun `given long when convert toLocalDateTime then return readable date`() {
        //given
        val converter = DateConverter()
        val epochDay: Long = 946725071
        val expected = LocalDateTime.of(2000, 1, 1, 11, 11, 11)

        //when
        val actual = converter.toLocalDateTime(epochDay)

        //then
        actual shouldBe expected
    }


    @Test
    fun `given localDateTime when convert toLong then return epochDay`() {
        //given
        val converter = DateConverter()
        val date = LocalDateTime.of(2000, 1, 1, 11, 11, 11)
        val expected: Long = 946725071

        //when
        val actual = converter.toLong(date)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given null value when convert toLocalDateTime then return current time`() {
        //given
        val converter = DateConverter()
        val expected = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MINUTES)

        //when
        val actual = converter.toLocalDateTime(null).truncatedTo(ChronoUnit.MINUTES)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given null value when convert toLong then return not null and higher as min`() {
        //given
        val converter = DateConverter()
        val minLocalDate = LocalDateTime.MIN.toEpochSecond(ZoneOffset.UTC)

        //when
        val actual = converter.toLong(null)

        //then
        actual.shouldNotBeNull()
        actual shouldBeGreaterThan minLocalDate

    }
}