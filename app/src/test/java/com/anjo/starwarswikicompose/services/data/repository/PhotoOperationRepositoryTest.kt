package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApi
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PhotoOperationRepositoryTest {

    @RelaxedMockK
    lateinit var flickrApi: FlickrApi

    @InjectMockKs
    lateinit var operationRepository: PhotoOperationRepository

    @Test
    fun `given searchText when getSearchPhotosInfo then return flickrResponse`(): Unit = runTest {
        //given
        val expected =
            FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0, 0, 0, 0, emptyList()))
        val searchText = "searchText"

        coEvery { flickrApi.getSearchPhotosInfo(searchText = searchText) } returns expected

        //when
        val actual = operationRepository.getSearchPhotosInfo(searchText)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given mock  when getRecentPhotos then return flickrResponse`(): Unit = runTest {
        //given
        val expected =
            FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0, 0, 0, 0, emptyList()))

        coEvery { flickrApi.getRecentPhotos() } returns expected

        //when
        val actual = operationRepository.getRecentPhotos()

        //then
        actual shouldBe expected
    }
}