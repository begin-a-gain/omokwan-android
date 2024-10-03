package com.begin_a_gain.omokwang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.begin_a_gain.library.design.theme.OmokwangTheme
import com.begin_a_gain.feature.sign_in.SignInScreen
import com.begin_a_gain.library.design.theme.isDarkMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmokwangTheme(
                darkTheme = false
            ) {
                SignInScreen()
            }
        }
    }
}