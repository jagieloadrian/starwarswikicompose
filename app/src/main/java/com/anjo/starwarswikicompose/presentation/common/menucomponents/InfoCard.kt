package com.anjo.starwarswikicompose.presentation.common.menucomponents

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.anjo.starwarswikicompose.BuildConfig
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalHeight
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.TestTags.INFORMATION_DIALOG_TAG
import java.time.LocalDate

@Composable
fun InfoDialog(onDismissAction: () -> Unit) {
    val appName = stringResource(R.string.app_name)
    val versionName = BuildConfig.VERSION_NAME
    val versionCode = BuildConfig.VERSION_CODE
    val author = BuildConfig.AUTHOR
    val debug = BuildConfig.DEBUG
    val year = LocalDate.now().year
    val copyright = "Copyright \u00a9 $year, $author"
    val height = (getLocalHeight() / 3).dp
    Dialog(onDismissRequest = onDismissAction) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp)
            .testTag(INFORMATION_DIALOG_TAG),
                shape = RoundedCornerShape(16.dp)) {
            Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))) {
                Column(modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .alpha(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {
                    Spacer(modifier = Modifier)
                    Text(text = appName,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White)
                    InfoRow("Version name: ", versionName)
                    InfoRow("Version code: ", versionCode.toString())
                    if (debug) {
                        InfoRow("Is in debug mode: ", debug.toString())
                    }
                    Text(text = copyright,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White)
                    Row(modifier = Modifier
                            .padding(horizontal = EXTRA_SMALL_PADDING)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                                onClick = onDismissAction,
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(
                                        Color.White.copy(Constants.LESS_WHITE_BACKGROUND_COPY))
                        ) {
                            Text(text = "Close",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(fieldName: String, fieldDescription: String) {
    Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = fieldName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
        Text(text = fieldDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
    }
}
