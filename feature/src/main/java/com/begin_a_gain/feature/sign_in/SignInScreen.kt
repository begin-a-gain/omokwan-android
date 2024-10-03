package com.begin_a_gain.feature.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initKakaoSdk()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.signInWithKakao()
            }
        ) {
            Text(text = "KAKAO")
        }
    }
}