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
class GetRecentImagesUseCaseTest {
    @MockK
    lateinit var operationRepository: PhotoOperationRepository

    @InjectMockKs
    lateinit var getRecentImagesUseCase: GetRecentImagesUseCase

    @Test
    fun `given mock  when getRecentPhotos then return flickrResponse`() = runTest {
        //given
        val expected =
            FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0, 0, 0, 0, emptyList()))

        coEvery { operationRepository.getRecentPhotos() } returns expected

        //when
        val actual = getRecentImagesUseCase()

        //then
        actual shouldBe expected
    }
}
