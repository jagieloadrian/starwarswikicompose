package com.anjo.starwarswikicompose.presentation.screens.welcome

import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class WelcomeViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var welcomeViewModel: WelcomeViewModel

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnBoardingState then verify captured value`(boolean: Boolean) = runBlocking {
        //given
        val slot = slot<Boolean>()

        coEvery { useCases.saveOnboardingUseCase(capture(slot)) } answers { }

        //when
        welcomeViewModel.saveOnBoardingState(boolean)
        delay(50)

        //then
        slot.captured shouldBe boolean
    }
}