@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.lifecycle.SavedStateHandle
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.SOURCE_TYPE_ARGUMENT_KEY
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MovieViewModelTest {
    @RelaxedMockK
    private lateinit var useCases: UseCases

    @RelaxedMockK
    private lateinit var imageSliderUseCases: ImageSliderUseCases

    @RelaxedMockK
    private lateinit var deleteUseCases: DeleteUseCases

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @InjectMockKs
    private lateinit var movieViewModel: MovieViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given movie when getMovie then return movie and related images`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl")
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2")
        val expectedImages = listOf(imageSliderModel, imageSliderModel2)
        val expected = MovieDto(id = objectId, "Title", episodeId = "1", openingCrawl = "openingCrawl")

        coEvery { savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY) } returns objectId
        coEvery { savedStateHandle.get<SourceType>(SOURCE_TYPE_ARGUMENT_KEY) } returns SourceType.APOLLO
        coEvery { useCases.getMovieUseCase(objectId) } returns expected
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, FILMS) } returns expectedImages

        //when
        movieViewModel.getMovie()
        advanceUntilIdle()
        val actual = movieViewModel.selectedMovie.value
        val actualImages = movieViewModel.images.value

        //then
        actual.movieDto shouldBe expected
        actual.state shouldBe SUCCESS
        actualImages shouldBe expectedImages
    }

    @Test
    fun `given objectId and photoUrl when saveInDatabase then verify call`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val photoUrl = "photoUrl"
        val expected = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = FILMS)
        val slot = slot<ImageSliderModel>()

        //when
        movieViewModel.saveInDatabase(objectId, photoUrl)
        advanceUntilIdle()

        //then
        coVerify { imageSliderUseCases.addImageToRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe expected
    }

    @Test
    fun `given Images when refreshImages then return list of images`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl")
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2")
        val expected = listOf(imageSliderModel, imageSliderModel2)

        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, FILMS) } returns expected

        //when
        movieViewModel.refreshImages(objectId)
        advanceUntilIdle()
        val actual = movieViewModel.images.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given ImageSliderModel when deleteFromDatabase then verify call`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl")
        val slot = slot<ImageSliderModel>()

        //when
        movieViewModel.deleteFromDatabase(imageSliderModel)
        advanceUntilIdle()

        //then
        coVerify { imageSliderUseCases.deleteImageFromRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe imageSliderModel
    }
}