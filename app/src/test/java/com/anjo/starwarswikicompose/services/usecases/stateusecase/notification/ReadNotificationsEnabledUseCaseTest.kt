package com.anjo.starwarswikicompose.services.usecases.stateusecase.notification

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class ReadNotificationsEnabledUseCaseTest {
    @MockK
    lateinit var operationRepository: StateOperationRepository

    @InjectMockKs
    lateinit var readNotificationsEnabledUseCase: ReadNotificationsEnabledUseCase

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when readOnboardingUseCase then return expected response`(boolean: Boolean) = runTest {
        //given
        val expected = flow { emit(boolean) }

        coEvery { operationRepository.readNotificationEnabled() } returns expected
        //when
        val actual = readNotificationsEnabledUseCase()

        //then
        actual shouldBe expected
    }
}