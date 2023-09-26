package com.anjo.starwarswikicompose.utils

import androidx.annotation.DrawableRes
import com.anjo.starwarswikicompose.R

sealed class OnboardingPage(
        @DrawableRes
        val image: Int,
        val title: String,
        val description: String
) {
    object First : OnboardingPage(
            image = R.drawable.hellothere,
            title = "Hello There",
            description = "Are you a Star Wars fan? Because if you are then we have a great news for you!"
    )

    object Second : OnboardingPage(
            image = R.drawable.tieandx,
            title = "Fly",
            description = "Find your favorite heroes and learn some of the things that you didn't know about."
    )

    object Third : OnboardingPage(
            image = R.drawable.darthvader,
            title = "Explore",
            description = "Check out your favourite things in Star Wars, be always prepared."
    )

}