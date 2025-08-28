package com.anjo.starwarswikicompose.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.DotsIndicator
import com.anjo.starwarswikicompose.ui.theme.EXTRA_LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.welcomeScreenImageBackgroundColor
import com.anjo.starwarswikicompose.utils.Constants.GO_TO_APP
import com.anjo.starwarswikicompose.utils.Constants.LAST_ON_BOARDING_PAGE
import com.anjo.starwarswikicompose.utils.Constants.ON_BOARDING_PAGE_COUNT
import com.anjo.starwarswikicompose.utils.OnboardingPage
import com.anjo.starwarswikicompose.utils.TestTags.WELCOME_BUTTON_TAG

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
) {

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

    Scaffold(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.tertiary)
                .padding(padding)
        ) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                PagerScreen(onboardingPage = pages[page], index = page)
            }
            DotsIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                totalDots = ON_BOARDING_PAGE_COUNT,
                selectedIndex = pagerState.currentPage,
                selectedColor = Color.LightGray,
                unSelectedColor = Color.DarkGray
            )
            FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                welcomeViewModel.saveOnBoardingState(completed = true)
                navController.navigate(Screen.Home.route)
            }
        }
    }
}

@Composable
fun PagerScreen(onboardingPage: OnboardingPage, index: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.7f)
                .background(
                    brush = Brush.radialGradient(
                        MaterialTheme.colorScheme.welcomeScreenImageBackgroundColor
                    ),
                    shape = RoundedCornerShape(EXTRA_SMALL_PADDING),
                    alpha = 0.8f
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.7f),
                painter = painterResource(onboardingPage.image),
                contentDescription = stringResource(R.string.on_boarding_image) + index,
                contentScale = ContentScale.Fit

            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onboardingPage.title,
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .padding(top = SMALL_PADDING),
            text = onboardingPage.description,
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit,
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
                modifier = Modifier.testTag(WELCOME_BUTTON_TAG),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(text = GO_TO_APP)
            }
        }
    }
}
