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
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.presentation.common.button.SaveButton
import com.anjo.starwarswikicompose.presentation.common.modify.addChunkField
import com.anjo.starwarswikicompose.presentation.common.modify.addChunksField
import com.anjo.starwarswikicompose.presentation.common.modify.addImageObject
import com.anjo.starwarswikicompose.presentation.common.modify.addListField
import com.anjo.starwarswikicompose.presentation.common.modify.addStringField
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.presentation.screens.create.RunLaunchEffect
import com.anjo.starwarswikicompose.presentation.screens.create.downloadRequiredChunks
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_SPECIE
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_SPECIE_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddSpecieObject(modifier: Modifier = Modifier,
                    specieDto: SpecieDto,
                    viewModel: AddObjectViewModel,
                    resetObject: () -> Unit = {},
                    onSaveClicked: (String) -> Unit) {

    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var specieDto by remember { mutableStateOf(specieDto) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val movieState by viewModel.movieChunks.collectAsState()
    val planetState by viewModel.planetChunks.collectAsState()
    val charState by viewModel.charChunks.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        downloadRequiredChunks(viewModel, listOf(PEOPLE, PLANETS, FILMS))
    }

    Box(modifier = modifier
            .fillMaxSize()
            .testTag(ADD_OBJECT_SPECIE_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            specieDto = specieDto.copy(name = addStringField(focusManager = focusManager,
                    placeholder = "Provide name of specie", label = "Specie",
                    firstValue = specieDto.name) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(language = addStringField(focusManager = focusManager,
                    placeholder = "Provide language of this specie", label = "Language",
                    firstValue = specieDto.language) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(homeworld = addChunkField(focusManager = focusManager,
                    placeholder = "Which planet is homeworld?", label = "Homeworld",
                    potentialChunks = planetState.chunks, firstValue = specieDto.homeworld))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(classification = addStringField(focusManager = focusManager,
                    placeholder = "What kind is your specie?", label = "Classification",
                    firstValue = specieDto.classification) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(designation = addStringField(focusManager = focusManager,
                    placeholder = "How you call them?", label = "Designation",
                    firstValue = specieDto.designation) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(averageHeight = addStringField(focusManager = focusManager,
                    placeholder = "How tall are they?", label = "Avg height",
                    firstValue = specieDto.averageHeight) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(averageLifespan = addStringField(focusManager = focusManager,
                    placeholder = "How long are the live?", label = "Avg lifespan",
                    firstValue = specieDto.averageLifespan) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(eyeColors = addListField(focusManager = focusManager,
                    placeholder = "Typical eye colors are?", label = "Eye colors", firstValue = specieDto.eyeColors))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(hairColors = addListField(focusManager = focusManager,
                    placeholder = "Typical hair colors are?", label = "Hair colors", firstValue = specieDto.hairColors))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(skinColors = addListField(focusManager = focusManager,
                    placeholder = "Typical skin colors are?", label = "Skin colors", firstValue = specieDto.skinColors))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(characterConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which heroes are your specie?",
                    label = HEROES_NAME, potentialChunks = charState.chunks,
                    firstValue = specieDto.characterConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            specieDto = specieDto.copy(movieConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which movies appear your specie?", label = MOVIES_NAME,
                    potentialChunks = movieState.chunks, firstValue = specieDto.movieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap = addImageObject(context, specieDto.toChunkDto(specieDto.id,
                    getSourceType(specieDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))


            SaveButton(ADD_NEW_SPECIE, isSaving, { verifyDto(specieDto) }) {
                isSaving = true
                viewModel.insertSpecie(specieDto.copy(id = ""))
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving,
            { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(specieDto: SpecieDto): Boolean {
    return specieDto.name.isNotEmpty() && specieDto.language.isNotEmpty()
}
