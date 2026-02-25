package com.begin_a_gain.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
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
            SplashSideEffect.NotFinishedSignUp, SplashSideEffect.LoggedOut -> {
                navigateToSignIn()
            }
            SplashSideEffect.LoggedIn -> {
                navigateToMain()
            }
        }
    }

    SplashContent()
}

@Preview
@Composable
fun SplashContent() {
    Column(
        modifier = Modifier.fillMaxSize().background(ColorToken.UI_PRIMARY.color()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OImage(
            image = OImageRes.SplashLogo,
            size = 154.dp
        )
    }
}