package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val tabs = Category.entries.toTypedArray()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
    ) {
        tabs.size
    }
    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val sytemBarColor = MaterialTheme.colors.mainBackgroundColors

    SideEffect {
        systemUiController.setStatusBarColor(
                color = sytemBarColor
        )
    }
    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) }
    ) {
        Column(modifier = Modifier.fillMaxSize()
                .padding(it)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = MaterialTheme.colors.mainBackgroundColors) {
                tabs.forEachIndexed { index, category ->
                    val selected = selectedIndex == index
                    CustomTab(selected, category) {
                        selectedIndex = index
                        scope.launch { pagerState.animateScrollToPage(index) }
                    }
                }
            }
            TabContent(navController, pagerState, tabs)
        }
    }
}

@Composable
private fun CustomTab(
        selected: Boolean,
        enum: Category, onClick: () -> Unit,
) {
    Tab(
            selected = selected,
            modifier = Modifier
                    .clip(RoundedCornerShape(25))
                    .background(MaterialTheme.colors.mainBackgroundColors),
            onClick = onClick,
            text = {
                Text(
                        text = enum.categoryName,
                        modifier = Modifier.padding(SMALL_PADDING),
                        textAlign = TextAlign.Center,
                        color = Color.White
                )
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(navController: NavHostController, state: PagerState, tabs: Array<Category>) {
    HorizontalPager(
            modifier = Modifier
                    .fillMaxSize(),
            state = state) { page ->
        CommonList(navController, tabs[page])
    }
}
