package com.anjo.starwarswikicompose

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
            val paused = remember{mutableStateOf(false)}
            val player1: MediaPlayer = remember {MediaPlayer.create(current, R.raw.cantinaband)}

            BackgroundMusic(lifecycleOwner, player1, paused)

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

    @Composable
    private fun BackgroundMusic(lifecycleOwner: LifecycleOwner, player1: MediaPlayer,
                          paused: MutableState<Boolean>) {
        DisposableEffect(key1 = lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME ||
                        event == Lifecycle.Event.ON_CREATE ||
                        event == Lifecycle.Event.ON_START) {
                    player1.start()
                    paused.value = false
                } else if (event == Lifecycle.Event.ON_STOP) {
                    player1.pause()
                    paused.value = true
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
}