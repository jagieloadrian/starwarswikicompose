package com.anjo.starwarswikicompose.presentation.screens.welcome

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.ui.theme.EXTRA_LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_SPACING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_WIDTH
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.buttonBackgroundColor
import com.anjo.starwarswikicompose.ui.theme.descriptionColor
import com.anjo.starwarswikicompose.ui.theme.titleColor
import com.anjo.starwarswikicompose.ui.theme.welcomeScreenBackgroundColor
import com.anjo.starwarswikicompose.utils.Constants.GO_TO_APP
import com.anjo.starwarswikicompose.utils.Constants.LAST_ON_BOARDING_PAGE
import com.anjo.starwarswikicompose.utils.Constants.ON_BOARDING_PAGE_COUNT
import com.anjo.starwarswikicompose.utils.OnboardingPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
        navController: NavHostController,
        welcomeViewModel: WelcomeViewModel = hiltViewModel()) {

    val pages = listOf(
            OnboardingPage.First,
            OnboardingPage.Second,
            OnboardingPage.Third
    )
    val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
    ) {
       pages.size
    }

    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.Top
                ){ page ->
            PagerScreen(onboardingPage = pages[page])

        }
        Row(
                modifier = Modifier.height(NAME_PLACEHOLDER_HEIGHT)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
        ) {
            repeat(ON_BOARDING_PAGE_COUNT) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                        modifier = Modifier.padding(PAGING_INDICATOR_SPACING)
                                .clip(CircleShape)
                                .background(color)
                                .size(PAGING_INDICATOR_WIDTH)
                )
            }
        }
        FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
        ) {
            Log.i(this.javaClass.simpleName, "FinishButton clicked")
            navController.navigate(Screen.Home.route)
            welcomeViewModel.saveOnBoardingState(completed = true)
        }
    }
}

@Composable
fun PagerScreen(onboardingPage: OnboardingPage) {
    Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
    ) {
        Image(
                modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.7f),
                painter = painterResource(onboardingPage.image),
                contentDescription = stringResource(R.string.on_boarding_image)
        )
        Text(
                modifier = Modifier
                        .fillMaxWidth(),
                text = onboardingPage.title,
                color = MaterialTheme.colors.titleColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
        )
        Text(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .padding(top = SMALL_PADDING),
                text = onboardingPage.description,
                color = MaterialTheme.colors.descriptionColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinishButton(
        modifier: Modifier,
        pagerState: PagerState,
        onClick: () -> Unit
) {
    Row(
            modifier = modifier
                    .padding(horizontal = EXTRA_LARGE_PADDING),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = pagerState.currentPage == LAST_ON_BOARDING_PAGE

        ) {
            Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                            contentColor = Color.White
                    )
            ) {
                Text(text = GO_TO_APP)
            }
        }
    }
}
