package com.begin_a_gain.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.OTextStyle
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    viewModel.collectSideEffect {
        when(it) {
            SplashSideEffect.NotFinishedSignUp -> {
                navigateToSignIn()
            }
            SplashSideEffect.LoggedIn -> {
                navigateToMain()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OText(text = "Omokwan", style = OTextStyle.Display)
    }
}