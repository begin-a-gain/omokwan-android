package com.begin_a_gain.omokwang

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.begin_a_gain.design.theme.OmokwangTheme
import com.begin_a_gain.omokwang.navigation.OmokwanGraph
import com.begin_a_gain.omokwang.navigation.SignIn
import com.begin_a_gain.omokwang.navigation.Splash
import com.begin_a_gain.omokwang.navigation.popAndNavigate
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    viewModel.collectSideEffect {
        when(it) {
            MainSideEffect.Logout -> navController.popAndNavigate(SignIn)
        }
    }

    OmokwangTheme(
        darkTheme = false
    ) {
        OmokwanGraph(
            navController = navController,
            startDestination = Splash
        )
    }
}