package com.anjo.starwarswikicompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.navigation.SetupNavGraph
import com.anjo.starwarswikicompose.services.usecases.UseCases
import com.anjo.starwarswikicompose.ui.theme.StarWarsWikiComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @Inject
    lateinit var useCases: UseCases

    private val mainViewModel: MainViewModel by viewModels()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private var completed = false

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val current = LocalContext.current
            mainViewModel.createMusic(current)
            BackgroundMusicLaunching(lifecycleOwner, mainViewModel)

            StarWarsWikiComposeTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController,
                        startDestination = if (completed) Screen.Home.route else Screen.Welcome.route,
                        modifier = Modifier)
            }
        }
        lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            useCases.readOnboardingUseCase().collect {
                completed = it
            }
        }

    }
}

@Composable
private fun BackgroundMusicLaunching(lifecycleOwner: LifecycleOwner,
                            mainViewModel: MainViewModel) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME ||
                    event == Lifecycle.Event.ON_CREATE ||
                    event == Lifecycle.Event.ON_START) {
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