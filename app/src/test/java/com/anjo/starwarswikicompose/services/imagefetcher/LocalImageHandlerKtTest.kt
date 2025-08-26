package com.anjo.starwarswikicompose.services.imagefetcher

import android.content.Context
import android.graphics.Bitmap
import com.anjo.starwarswikicompose.domain.model.sw.Category
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.FileOutputStream
import kotlin.io.path.createTempFile

class LocalImageHandlerKtTest {

    @ParameterizedTest
    @EnumSource(value = Category::class)
    fun `given id and category when findImageAsset then return path`(category: Category) {
        //given
        val id = "objectId"
        val expected = "file:///android_asset/${category.categoryName.lowercase()}/objectId.jpg"
        val mockContext = mockk<Context>()

        //when
        val actual = findImageAsset(id, category, false, mockContext)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given id and isFromLocalStorage when findImageAsset then return path`() {
        //given
        val id = "objectId"
        val expected = "mockPath/$id.jpg"
        val mockContext = mockk<Context>()
        every { mockContext.filesDir.absolutePath } returns "mockPath"

        //when
        val actual = findImageAsset(id, Category.ALL, true, mockContext)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given mocks when saveImage then check interactions`() {
        //given
        val mockContext = mockk<Context> {
            every { filesDir } returns createTempFile().parent.toFile()
        }
        val image: Bitmap = mockk {
            every { compress(Bitmap.CompressFormat.JPEG, 100, any()) } returns true
        }
        mockkConstructor(FileOutputStream::class)
        every {
            anyConstructed<FileOutputStream>().write(any<ByteArray>())
        } returns Unit

        //when
        saveImage(mockContext, image, "newMockName")

        //then
        verify(exactly = 1) { anyConstructed<FileOutputStream>().write(any<ByteArray>()) }
    }
}