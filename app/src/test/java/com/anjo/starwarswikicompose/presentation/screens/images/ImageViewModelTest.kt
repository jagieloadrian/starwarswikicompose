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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ImageViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var imageViewModel: ImageViewModel

    @Test
    fun `given flickrResponse when fetchRecentPhotos then return response`() = runBlocking {
        //given
        val flickrPhotos = FlickrPhotos(0, 1, 0, 0, listOf())
        val expected = FlickrResponse(photos = flickrPhotos, stat = ok)
        coEvery { useCases.getRecentImagesUseCase() } returns expected

        //when
        imageViewModel.fetchRecentPhotos()
        delay(50)
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given flickrResponse when fetchPhotoInfo then return response`() = runBlocking {
        //given
        val sampleQuery = "sampleQuery"
        val flickrPhotos = FlickrPhotos(0, 1, 0, 0, listOf())
        val expected = FlickrResponse(photos = flickrPhotos, stat = ok)
        coEvery { useCases.getSearchImagesUseCase(sampleQuery) } returns expected

        //when
        imageViewModel.fetchPhotoInfo(sampleQuery)
        delay(50)
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
    fun `given flickrResponse when fetchRecentPhotos and throw exc then return with error name`() = runBlocking {
        //given
        coEvery { useCases.getRecentImagesUseCase() } throws HttpException(null, null)

        //when
        imageViewModel.fetchRecentPhotos()
        delay(50)
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual.message shouldBe ERROR_UNAVAILABLE_INTERNET
    }

    @Test
    fun `given flickrResponse when fetchPhotoInfo and throw exc then return response with error name`() = runBlocking {
        //given
        val sampleQuery = "sampleQuery"
        coEvery { useCases.getSearchImagesUseCase(sampleQuery) } throws HttpException(null, null)

        //when
        imageViewModel.fetchPhotoInfo(sampleQuery)
        delay(50)
        val actual = imageViewModel.fetchedPhotoInfos.value

        //then
        actual.message shouldBe ERROR_UNAVAILABLE_INTERNET
    }
}