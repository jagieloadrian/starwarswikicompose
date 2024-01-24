package com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class SaveOnboardingUseCaseTest{

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var saveOnboardingUseCase: SaveOnboardingUseCase

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnboardingUseCase then verify call`(boolean: Boolean) = runBlocking {
        //given
        val slot = slot<Boolean>()

        coEvery { operationRepository.saveOnboardingState(capture(slot)) } returns Unit
        //when
        saveOnboardingUseCase(boolean)

        //then
        slot.captured shouldBe boolean
    }
}