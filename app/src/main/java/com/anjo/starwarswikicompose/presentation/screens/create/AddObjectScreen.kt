package com.anjo.starwarswikicompose.presentation.screens.create

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.ALL
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.presentation.common.create.models.AddMovieObject
import com.anjo.starwarswikicompose.presentation.common.create.models.AddPersonObject
import com.anjo.starwarswikicompose.presentation.common.create.models.AddPlanetObject
import com.anjo.starwarswikicompose.presentation.common.create.models.AddSpecieObject
import com.anjo.starwarswikicompose.presentation.common.create.models.AddStarshipObject
import com.anjo.starwarswikicompose.presentation.common.create.models.AddVehicleObject
import com.anjo.starwarswikicompose.presentation.screens.home.CategoryDropDown
import com.anjo.starwarswikicompose.services.imagefetcher.saveImage
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_CATEGORY_DROPDOWN
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_MAIN_SCREEN
import com.anjo.starwarswikicompose.utils.TestTags.SHOW_MEME_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

@ExperimentalMaterial3Api
@Composable
fun AddObjectScreen(modifier: Modifier = Modifier, category: Category,
                    viewModel: AddObjectViewModel = hiltViewModel(),
                    onDismiss: (Boolean, Category) -> Unit) {
    var categoryState by remember { mutableStateOf(category) }
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)

    ModalBottomSheet(
            modifier = modifier.testTag(ADD_OBJECT_MAIN_SCREEN),
            onDismissRequest = { onDismiss(false, ALL) },
            sheetState = sheetState,
            containerColor = Color.Transparent,
    ) {
        Column(modifier = modifier
                .fillMaxWidth()
                .paint(painter = painterResource(R.drawable.night_sky_stars),
                        contentScale = ContentScale.FillBounds),
                horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = modifier.height(MEDIUM_PADDING))
            CategoryDropDown(modifier = modifier
                    .height(HOME_ICON_HEIGHT)
                    .clip(RoundedCornerShape(SMALL_PADDING))
                    .border(SMALL_BORDER, color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(SMALL_PADDING))
                    .testTag(ADD_OBJECT_CATEGORY_DROPDOWN), selectedCategory = categoryState) { cat ->
                categoryState = cat
            }
            Spacer(modifier = modifier.height(SMALL_PADDING))
            when (categoryState) {
                ALL       -> ShowMeme()
                FILMS     -> AddMovieObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        movieDto = MovieDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }

                PEOPLE    -> AddPersonObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        personDto = PersonDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }

                PLANETS   -> AddPlanetObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        planetDto = PlanetDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }

                SPECIES   -> AddSpecieObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        specieDto = SpecieDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }

                STARSHIPS -> AddStarshipObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        starshipDto = StarshipDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }

                VEHICLES  -> AddVehicleObject(modifier, viewModel = viewModel,
                        resetObject = { viewModel.cleanCurrentObjectId() },
                        vehicleDto = VehicleDto(isFromLocalStore = true)) { onDismiss(true, categoryState) }
            }
        }
    }
}

@Composable
fun ShowMeme() {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 2500,
                            easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            ), label = ""
    )

    Image(painter = painterResource(R.drawable.choose_one),
            contentDescription = stringResource(R.string.network_error_icon),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                    .padding(SMALL_PADDING)
                    .size(VEHICLE_PICTURE_HEIGHT)
                    .clip(RoundedCornerShape(MEDIUM_PADDING))
                    .alpha(alphaAnim)
                    .testTag(SHOW_MEME_TAG)
    )
}

fun downloadRequiredChunks(viewModel: AddObjectViewModel, categories: List<Category>) {
    categories.forEach { category -> viewModel.getChunksByCategory(category) }
}


@Composable
fun RunLaunchEffect(currentId: StateFlow<String>, context: Context,
                    imageBitmap: Bitmap?, isSaving: Boolean, resetObject: () -> Unit, onSaveClick: () -> Unit) {
    LaunchedEffect(isSaving) {
        if (isSaving) {
            val id = withTimeoutOrNull(3000) {
                currentId.filter { it.isNotEmpty() }.first()
            }
            imageBitmap?.let { bitmap ->
                id?.let {
                    saveImage(context, bitmap, it)
                }
            }
            resetObject()
            delay(1500)
            onSaveClick()
        }
    }
}
