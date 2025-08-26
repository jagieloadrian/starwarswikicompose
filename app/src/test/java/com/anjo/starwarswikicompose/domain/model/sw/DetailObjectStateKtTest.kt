package com.anjo.starwarswikicompose.domain.model.sw

import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.ERROR
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.LOADING
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class DetailObjectStateKtTest {

    @ParameterizedTest
    @MethodSource("isSuccessSource")
    fun ` given enum state when isSuccess then return true`(state: DetailObjectState, expected: Boolean) {
        //when
        val actual = state.isSuccess()
        //then
        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("isErrorSource")
    fun ` given enum state when isError then return true`(state: DetailObjectState, expected: Boolean) {
        //when
        val actual = state.isError()
        //then
        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("isLoadingSource")
    fun ` given enum state when isLoading then return true`(state: DetailObjectState, expected: Boolean) {
        //when
        val actual = state.isLoading()
        //then
        actual shouldBe expected
    }

    companion object {
        @JvmStatic
        fun isSuccessSource(): List<Arguments> {
            return listOf(
                    Arguments.of(SUCCESS, true),
                    Arguments.of(ERROR, false),
                    Arguments.of(LOADING, false))
        }

        @JvmStatic
        fun isErrorSource(): List<Arguments> {
            return listOf(
                    Arguments.of(SUCCESS, false),
                    Arguments.of(ERROR, true),
                    Arguments.of(LOADING, false))
        }

        @JvmStatic
        fun isLoadingSource(): List<Arguments> {
            return listOf(
                    Arguments.of(SUCCESS, false),
                    Arguments.of(ERROR, false),
                    Arguments.of(LOADING, true))
        }
    }
}