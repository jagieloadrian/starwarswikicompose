@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.images

import android.net.http.HttpException
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.ok
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.ERROR_UNAVAILABLE_INTERNET
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
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
class ImageViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases
    private val testDispatcher = StandardTestDispatcher()

    @InjectMockKs
    private lateinit var imageViewModel: ImageViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given flickrResponse when fetchRecentPhotos then return response`() = runTest {
        //given
        val flickrPhotos = FlickrPhotos(0, 1, 0, 0, listOf())
        val expected = FlickrResponse(photos = flickrPhotos, stat = ok)
        coEvery { useCases.getRecentImagesUseCase() } returns expected

        //when
        imageViewModel.fetchRecentPhotos()
        advanceUntilIdle()
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given flickrResponse when fetchPhotoInfo then return response`() = runTest {
        //given
        val sampleQuery = "sampleQuery"
        val flickrPhotos = FlickrPhotos(0, 1, 0, 0, listOf())
        val expected = FlickrResponse(photos = flickrPhotos, stat = ok)
        coEvery { useCases.getSearchImagesUseCase(sampleQuery) } returns expected

        //when
        imageViewModel.fetchPhotoInfo(sampleQuery)
        advanceUntilIdle()
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given query when updateSearchQuery then return expected query`() {
        //given
        val expected = "sampleQuery"

        //when
        imageViewModel.updateSearchQuery(expected)
        val actual = imageViewModel.searchQuery.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given flickrResponse when fetchRecentPhotos and throw exc then return with error name`() = runTest {
        //given
        coEvery { useCases.getRecentImagesUseCase() } throws HttpException(null, null)

        //when
        imageViewModel.fetchRecentPhotos()
        advanceUntilIdle()
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual.message shouldBe ERROR_UNAVAILABLE_INTERNET
    }

    @Test
    fun `given flickrResponse when fetchPhotoInfo and throw exc then return response with error name`() = runTest {
        //given
        val sampleQuery = "sampleQuery"
        coEvery { useCases.getSearchImagesUseCase(sampleQuery) } throws HttpException(null, null)

        //when
        imageViewModel.fetchPhotoInfo(sampleQuery)
        advanceUntilIdle()
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual.message shouldBe ERROR_UNAVAILABLE_INTERNET
    }
}