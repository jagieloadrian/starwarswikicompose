package com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class ReadOnboardingUseCaseTest{

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var readOnboardingUseCase: ReadOnboardingUseCase

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when readOnboardingUseCase then return expected response`(boolean: Boolean) = runBlocking {
        //given
        val expected = flow { emit(boolean) }

        coEvery { operationRepository.readOnboardingState() } returns expected
        //when
        val actual = readOnboardingUseCase()

        //then
        actual shouldBe expected
    }
}