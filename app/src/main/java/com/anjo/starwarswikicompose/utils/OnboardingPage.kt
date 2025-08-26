package com.anjo.starwarswikicompose.utils

import androidx.annotation.DrawableRes
import com.anjo.starwarswikicompose.R

sealed class OnboardingPage(
        @param:DrawableRes
        val image: Int,
        val title: String,
        val description: String,
) {
    data object First : OnboardingPage(
            image = R.drawable.hellothere,
            title = "Hello There",
            description = "Are you a Star Wars fan? Because if you are then we have a great news for you!"
    )

    data object Second : OnboardingPage(
            image = R.drawable.tiefighter,
            title = "Fly",
            description = "Find your favorite heroes and learn some of the things that you didn't know about."
    )

    data object Third : OnboardingPage(
            image = R.drawable.darth_vader,
            title = "Explore",
            description = "Check out your favourite things in Star Wars, be always prepared."
    )
}