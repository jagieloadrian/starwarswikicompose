package com.anjo.starwarswikicompose.presentation.screens.person.home

import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HomePersonViewModelTest{

    @RelaxedMockK
    private lateinit var useCases: UseCases
    @InjectMockKs
    private lateinit var homePersonViewModel: HomePersonViewModel

    @Test
    fun `given peopleState when getPeople then update peopleState`() = runBlocking {
        //given
        val expected = HomePersonViewModel.PeopleState(people = listOf(), isLoading = true)

        //when
        homePersonViewModel.getPeople()
        delay(50)

        val actual = homePersonViewModel.fetchedPeople.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given peopleState when fetchPeople then update peopleState`() = runBlocking {
        //given
        val objects = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomePersonViewModel.PeopleState(people = objects, isLoading = false)

        coEvery { useCases.getAllPeopleUseCase() } returns objects

        //when
        homePersonViewModel.fetchPeople()
        delay(50)

        val actual = homePersonViewModel.fetchedPeople.value

        //then
        actual shouldBe expected
    }
}