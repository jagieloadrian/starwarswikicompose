package com.anjo.starwarswikicompose.navigation

import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import io.kotest.matchers.shouldBe
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(MockKExtension::class)
class NavGraphKtTest {

    @RelaxedMockK
    lateinit var navHostController: NavHostController

    @ParameterizedTest
    @EnumSource(value = Category::class, mode = EnumSource.Mode.EXCLUDE, names = ["ALL"])
    fun `given id when navigateToProperlyCompose then verify path`(category: Category) {
        //given
        val id = "123sl2"
        val chunk = UniversalChunkDto(id = id, category = category)
        val slot = slot<String>()
        val expected = "${findProperlyName(category)}/$id"

        //when
        navigateToProperlyCompose(navHostController, chunk)

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
            Category.ALL       -> "bbb"
        }
    }

}