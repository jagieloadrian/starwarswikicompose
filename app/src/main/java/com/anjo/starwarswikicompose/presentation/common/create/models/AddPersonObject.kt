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
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.presentation.common.button.SaveButton
import com.anjo.starwarswikicompose.presentation.common.modify.addChunkField
import com.anjo.starwarswikicompose.presentation.common.modify.addChunksField
import com.anjo.starwarswikicompose.presentation.common.modify.addImageObject
import com.anjo.starwarswikicompose.presentation.common.modify.addStringField
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.presentation.screens.create.RunLaunchEffect
import com.anjo.starwarswikicompose.presentation.screens.create.downloadRequiredChunks
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_HERO
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_PERSON_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddPersonObject(modifier: Modifier = Modifier,
                    personDto: PersonDto,
                    viewModel: AddObjectViewModel,
                    resetObject: () -> Unit = {},
                    onSaveClicked: (String) -> Unit) {

    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var personDto by remember { mutableStateOf(personDto) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val movieState by viewModel.movieChunks.collectAsState()
    val planetState by viewModel.planetChunks.collectAsState()
    val starshipState by viewModel.starshipChunks.collectAsState()
    val vehicleState by viewModel.vehicleChunks.collectAsState()
    val speciesState by viewModel.specieChunks.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        downloadRequiredChunks(viewModel, listOf(FILMS, PLANETS, STARSHIPS, VEHICLES, SPECIES))
    }

    Box(modifier = modifier
            .fillMaxSize()
            .testTag(ADD_OBJECT_PERSON_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            personDto = personDto.copy(name = addStringField(focusManager = focusManager,
                    placeholder = "Provide name of hero", label = "Hero",
                    firstValue = personDto.name) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    homeworld = addChunkField(focusManager = focusManager,
                            placeholder = "Which planet is homeworld?",
                            label = "Homeworld", potentialChunks = planetState.chunks,
                            firstValue = personDto.homeworld))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    specie = addChunkField(focusManager = focusManager, placeholder = "What kind is your hero?",
                            label = "Specie", potentialChunks = speciesState.chunks, firstValue = personDto.specie))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    birthYear = addStringField(focusManager = focusManager, placeholder = "When your hero was born?",
                            label = "Birth Year", firstValue = personDto.birthYear) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    height = addStringField(focusManager = focusManager, placeholder = "How tall is your hero?",
                            label = "Height", firstValue = personDto.height) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(mass = addStringField(focusManager = focusManager,
                    placeholder = "How heavy is your hero?", label = "Mass",
                    firstValue = personDto.mass) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(gender = addStringField(focusManager = focusManager,
                    placeholder = "What gender it is?", label = "Gender",
                    firstValue = personDto.gender) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    hair = addStringField(focusManager = focusManager, placeholder = "What hair has your hero?",
                            label = "Hair", firstValue = personDto.hair) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(
                    skin = addStringField(focusManager = focusManager, placeholder = "What skin has your hero?",
                            label = "Skin", firstValue = personDto.skin) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(starshipConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which spaceships use your hero?",
                    label = STARSHIPS_NAME, potentialChunks = starshipState.chunks,
                    firstValue = personDto.starshipConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(vehicleConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which vehicles use your hero?",
                    label = VEHICLES_NAME, potentialChunks = vehicleState.chunks,
                    firstValue = personDto.vehicleConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            personDto = personDto.copy(movieConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which movies appear your hero?", label = MOVIES_NAME,
                    potentialChunks = movieState.chunks, firstValue = personDto.movieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap = addImageObject(context, personDto.toChunkDto(personDto.id,
                    getSourceType(personDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))

            SaveButton(ADD_NEW_HERO, isSaving, { verifyDto(personDto) }) {
                isSaving = true
                viewModel.insertPerson(personDto.copy(id = ""))
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving, { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(personDto: PersonDto): Boolean {
    return personDto.name.isNotEmpty() && personDto.birthYear.isNotEmpty()
}