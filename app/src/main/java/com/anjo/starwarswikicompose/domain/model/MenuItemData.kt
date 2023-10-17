package com.anjo.starwarswikicompose.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.anjo.starwarswikicompose.R

enum class MenuItemData(val text: String) {
    Notes("Notes"),
    Mail("Mail"),
    Info("Info"),
    SoundOn("Sound"),
    Mute("Mute");

    val icon: Painter
        @Composable
        get() = when (this) {
            Notes   -> painterResource(R.drawable.baseline_notes_24)
            Mail    -> painterResource(R.drawable.outline_mail_24)
            Info    -> painterResource(R.drawable.outline_info_24)
            SoundOn -> painterResource(R.drawable.outline_volume_up_24)
            Mute    -> painterResource(R.drawable.baseline_volume_mute_24)
        }

}