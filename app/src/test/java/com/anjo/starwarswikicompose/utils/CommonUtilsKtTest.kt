package com.anjo.starwarswikicompose.utils

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.navigation.Screen
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(MockKExtension::class)
class CommonUtilsKtTest {

    @RelaxedMockK
    lateinit var navHostController: NavHostController

    @RelaxedMockK
    lateinit var clipboardManager: ClipboardManager

    @Test
    fun `given arguments when calculatePathToImage then return properly path`() {
        //given
        val category = Category.FILMS
        val id = "randomId"
        val expected = "${Constants.ASSETS_PATH}/films/randomId.jpg"

        //when
        val actual = calculatePathToImage(category, id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given object photo when buildImageUrl then return string with url`() {
        //given
        val photo = FlickrPhoto(id = "randomId", owner = "12345", secret = "secret123", server = "server123",
                farm = 1, title = "randomTitle", isPublic = 1, isFamily = 1, isFriend = 1, ownername = "ownerName")

        val expected = "${Constants.FLICKR_BASE_URL_IMAGE}/server123/randomId_secret123${Constants.FLICKR_EXT}"

        //when
        val actual = buildImageUrl(photo)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given wrongly photoUrl when validateUrl then return false`() {
        //given
        val badUrl = "thisIsBadUrl"
        val expected = false

        //when
        val actual = validateUrl(badUrl)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given properly photoUrl when validateUrl then return true`() {
        //given
        val url = "${Constants.FLICKR_BASE_URL_IMAGE}/server123/randomId_secret123${Constants.FLICKR_EXT}"
        val expected = true

        //when
        val actual = validateUrl(url)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given properly url when addImageFunction then return url`() {
        //given
        val expectedUrl = "${Constants.FLICKR_BASE_URL_IMAGE}/server123/randomId_secret123${Constants.FLICKR_EXT}"
        var actual = ""
        every { clipboardManager.getText() } returns AnnotatedString(expectedUrl)

        //when
        addImageFunction(clipboardManager, navHostController, runSaving = { actual = it }, {}, {})

        //then
        actual shouldBe expectedUrl
    }

    @Test
    fun `given wrongly url when addImageFunction then verify navController`() {
        //given
        val expectedUrl = "notAcceptedUrl"
        every { clipboardManager.getText() } returns AnnotatedString(expectedUrl)

        //when
        addImageFunction(clipboardManager, navHostController, {}, {}, {})

        //then
        verify { navHostController.navigate(Screen.ImageSearch.route) }
    }

    @ParameterizedTest
    @EnumSource(value = Category::class)
    fun `given id when navigateToProperlyCompose then verify path`(category: Category) {
        //given
        val id = "123sl2"
        val slot = slot<String>()
        val expected = "${findProperlyName(category)}/$id"

        //when
        navigateToProperlyCompose(navHostController, id, category)

        //then
        verify { navHostController.navigate(route = capture(slot)) }
        slot.captured shouldBe expected
    }

    private fun findProperlyName(category: Category): String {
        return when (category) {
            Category.FILMS     -> "details_movie"
            Category.PEOPLE    -> "details_person"
            Category.PLANETS   -> "details_planet"
            Category.SPECIES   -> "details_specie"
            Category.STARSHIPS -> "details_starship"
            Category.VEHICLES  -> "details_vehicle"
        }
    }

    @ParameterizedTest
    @EnumSource(value = Category::class)
    fun `given film object when convert to image model then return imageslidermodel`(category: Category) {
        //given
        val id = "exampleId"
        val examplePhotoUrl = "photo.url"
        val universalChunk = UniversalChunk(id = id, name = "", desc = "")
        val expected = ImageSliderModel(
                objectId = id,
                url = examplePhotoUrl,
                objectType = category
        )
        //when
        val actual = universalChunk.toImageSliderModel(examplePhotoUrl, category)

        //then
        actual shouldBe expected
    }
}
