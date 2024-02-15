package com.anjo.starwarswikicompose.presentation.screens.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.reverseMainBackgroundColors
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalHeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardNote(
        onDismissAction: () -> Unit,
        cardNoteViewModel: CardNoteViewModel = hiltViewModel(),
) {
    val height = ((getLocalHeight() / 3) * 2).dp
    val userText = remember { mutableStateOf("Default Value") }
    val init = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    if (init.value) {
        cardNoteViewModel.getNotes()
        scope.launch {
            delay(1000)
            userText.value = cardNoteViewModel.note.value.text
            init.value = false
        }
    }


    CardNoteDialog(height, init, userText ) {
        cardNoteViewModel.updateNote(userText.value)
        onDismissAction()
    }
}

@Composable
fun CardNoteDialog(
        height: Dp,
        init: MutableState<Boolean>,
        userText: MutableState<String>,
        onDismissAction: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissAction) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp)
                .testTag("NOTE_DIALOG"),
                shape = RoundedCornerShape(16.dp)) {
            Box(modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colors.mainBackgroundColors)
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))) {
                Column(modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colors.mainBackgroundColors,
                                shape = RoundedCornerShape(16.dp))
                        .alpha(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {
                    if (init.value) {
                        CircularProgressIndicator()
                    } else {
                        OutlinedTextField(value = userText.value,
                                onValueChange = { userText.value = it },
                                label = { Text(stringResource(R.string.label_notes)) },
                                modifier = Modifier.fillMaxSize()
                                        .weight(8f),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.White,
                                        focusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        focusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        cursorColor = MaterialTheme.colors.primary
                                ))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth()
                            .weight(0.2f))
                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                        TextButton(
                                onClick = {
                                    onDismissAction()
                                },
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(
                                        Color.White.copy(Constants.LESS_WHITE_BACKGROUND_COPY)),
                        ) {
                            Text(text = stringResource(R.string.save_close),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White)
                        }
                    }
                }
            }
        }
    }
}