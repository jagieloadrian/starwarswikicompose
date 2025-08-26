@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.welcome

import com.anjo.starwarswikicompose.services.usecases.stateusecase.StateUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class WelcomeViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: StateUseCase
    private val testDispatcher = StandardTestDispatcher()

    @InjectMockKs
    private lateinit var welcomeViewModel: WelcomeViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnBoardingState then verify captured value`(boolean: Boolean) = runTest {
        //given
        val slot = slot<Boolean>()

        coEvery { useCases.saveOnboardingUseCase(capture(slot)) } answers { }

        //when
        welcomeViewModel.saveOnBoardingState(boolean)
        advanceUntilIdle()

        //then
        slot.captured shouldBe boolean
    }
}