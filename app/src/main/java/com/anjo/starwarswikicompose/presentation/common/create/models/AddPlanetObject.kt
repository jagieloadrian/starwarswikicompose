package com.anjo.starwarswikicompose.presentation.common.create.models

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.core.text.isDigitsOnly
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.presentation.common.button.SaveButton
import com.anjo.starwarswikicompose.presentation.common.modify.addChunksField
import com.anjo.starwarswikicompose.presentation.common.modify.addImageObject
import com.anjo.starwarswikicompose.presentation.common.modify.addListField
import com.anjo.starwarswikicompose.presentation.common.modify.addStringField
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.presentation.screens.create.RunLaunchEffect
import com.anjo.starwarswikicompose.presentation.screens.create.downloadRequiredChunks
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_PLANET
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_PLANET_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddPlanetObject(modifier: Modifier = Modifier,
                    planetDto: PlanetDto,
                    viewModel: AddObjectViewModel,
                    resetObject: () -> Unit = {},
                    onSaveClicked: (String) -> Unit) {

    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var planetDto by remember { mutableStateOf(planetDto) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val movieState by viewModel.movieChunks.collectAsState()
    val charState by viewModel.charChunks.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        downloadRequiredChunks(viewModel, listOf(FILMS, PEOPLE))
    }

    Box(modifier = modifier
            .fillMaxSize()
            .testTag(ADD_OBJECT_PLANET_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            planetDto = planetDto.copy(
                    name = addStringField(focusManager = focusManager, placeholder = "Provide name of planet",
                            label = "Planet", firstValue = planetDto.name) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(
                    diameter = addStringField(focusManager = focusManager, placeholder = "How big is this planet?",
                            label = "Diameter", firstValue = planetDto.diameter) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(gravity = addStringField(focusManager = focusManager,
                    placeholder = "How heavy are you feeling on this planet?",
                    label = "Gravity", firstValue = planetDto.gravity) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(population = addStringField(focusManager = focusManager,
                    placeholder = "How many people can you meet? Only numbers",
                    label = "Population", firstValue = planetDto.population) { it.isNotEmpty() && it.isDigitsOnly() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(rotationPeriod = addStringField(focusManager = focusManager,
                    placeholder = "How fast is spin round?",
                    label = "Rotation Period", firstValue = planetDto.rotationPeriod) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(orbitalPeriod = addStringField(focusManager = focusManager,
                    placeholder = "How fast is planet to see other side of sun?",
                    label = "Orbital Period", firstValue = planetDto.orbitalPeriod) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(climates = addListField(focusManager = focusManager,
                    placeholder = "Is it cold or sunny? maybe both?",
                    label = "Climates", firstValue = planetDto.climates))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(
                    surfaceWater = addStringField(focusManager = focusManager, placeholder = "How big are oceans?",
                            label = "Surface Water", firstValue = planetDto.surfaceWater) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(terrains = addListField(focusManager = focusManager,
                    placeholder = "Is it sand or rocks? maybe both?",
                    label = "Terrains", firstValue = planetDto.terrains))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(characterConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which characters live at your planet?",
                    label = HEROES_NAME, potentialChunks = charState.chunks,
                    firstValue = planetDto.characterConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            planetDto = planetDto.copy(movieConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which movies appear your planet?",
                    label = MOVIES_NAME, potentialChunks = movieState.chunks,
                    firstValue = planetDto.movieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap = addImageObject(context, planetDto.toChunkDto(planetDto.id,
                    getSourceType(planetDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))

            SaveButton(ADD_NEW_PLANET, isSaving, { verifyDto(planetDto) }) {
                isSaving = true
                viewModel.insertPlanet(planetDto.copy(id = ""))
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving, { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(planetDto: PlanetDto): Boolean {
    return planetDto.name.isNotEmpty() && planetDto.population.isNotEmpty()
}