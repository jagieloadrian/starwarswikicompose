package com.anjo.starwarswikicompose.utils

import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.UnitName.CM
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.APOLLO
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.ROOM
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.NA
import com.anjo.starwarswikicompose.utils.Constants.UNKNOWN
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CommonUtilsKtTest {

    @ParameterizedTest
    @MethodSource("stringAndUnits")
    fun `given different descriptions when get description then return properly string`(
            givenString: String?,
            unitName: UnitName?,
            expected: String,
    ) {
        //when
        val actual = getDescriptionName(givenString, unitName)

        //then
        actual shouldBe expected

    }

    @ParameterizedTest
    @MethodSource("sourceAndBoolean")
    fun `given sourceType when check isFromLocalStorage then should return properly boolean`(
            sourceType: SourceType,
            expected: Boolean
    ) {
        //when
        val actual = isFromLocalStorage(sourceType)

        //then
        actual shouldBe expected
    }

    companion object {
        @JvmStatic
        fun stringAndUnits(): List<Arguments> {
            return listOf(
                    Arguments.of("description", null, "description"),
                    Arguments.of(null, null, EMOJI),
                    Arguments.of("  ", null, EMOJI),
                    Arguments.of("", null, EMOJI),
                    Arguments.of(UNKNOWN, null, EMOJI),
                    Arguments.of(NA, null, EMOJI),
                    Arguments.of("description", CM, "description cm"),
            )
        }

        @JvmStatic
        fun sourceAndBoolean(): List<Arguments> {
            return listOf(
                    Arguments.of(ROOM, true),
                    Arguments.of(APOLLO, false)
            )
        }
    }
}
