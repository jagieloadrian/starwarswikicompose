package com.anjo.starwarswikicompose.services.usecases.operationusecase.images

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import com.anjo.starwarswikicompose.services.data.repository.PhotoOperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetSearchImagesUseCaseTest {

    @MockK
    lateinit var operationRepository: PhotoOperationRepository

    @InjectMockKs
    lateinit var getSearchImagesUseCase: GetSearchImagesUseCase

    @Test
    fun `given searchText when getSearchImagesUseCase then return flickrResponse`() = runTest {
        //given
        val expected =
            FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0, 0, 0, 0, emptyList()))
        val searchText = "searchText"

        coEvery { operationRepository.getSearchPhotosInfo(searchText = searchText) } returns expected

        //when
        val actual = getSearchImagesUseCase(searchText)

        //then
        actual shouldBe expected
    }
}