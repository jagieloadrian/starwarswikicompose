package com.anjo.starwarswikicompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.navigation.SetupNavGraph
import com.anjo.starwarswikicompose.services.usecases.stateusecase.StateUseCase
import com.anjo.starwarswikicompose.ui.theme.StarWarsWikiComposeTheme
import com.anjo.starwarswikicompose.utils.TestTags.MAIN_NAV_GRAPH
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var useCases: StateUseCase
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavHostController
    private var completed = false

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Initialize the Google Mobile Ads SDK on a background thread.
        mobileAdsConfig()
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            mainViewModel.createMusic()
            BackgroundMusicLaunching(lifecycleOwner, mainViewModel)
            StarWarsWikiComposeTheme {
                val systemBarColor = MaterialTheme.colorScheme.primary
                SetStatusBarColor(color = systemBarColor)

                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    startDestination = if (completed) Screen.Home.route else Screen.Welcome.route,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
                        .testTag(MAIN_NAV_GRAPH),
                )
            }
        }
        lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            useCases.readOnboardingUseCase().collect {
                completed = it
            }
        }
    }

    private fun mobileAdsConfig() {
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTagForUnderAgeOfConsent(TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE)
                .setPublisherPrivacyPersonalizationState(RequestConfiguration.PublisherPrivacyPersonalizationState.DISABLED)
                .build()
        )
        MobileAds.initialize(this@MainActivity) {}
    }
}

@Composable
private fun BackgroundMusicLaunching(
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel,
) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME ||
                event == Lifecycle.Event.ON_CREATE ||
                event == Lifecycle.Event.ON_START
            ) {
                mainViewModel.playMusic()
            } else if (event == Lifecycle.Event.ON_STOP) {
                mainViewModel.pauseMusic()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun SetStatusBarColor(color: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.decorView.setBackgroundColor(color.toArgb())
        }
    }
}