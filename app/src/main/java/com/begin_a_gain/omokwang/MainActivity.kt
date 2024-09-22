package com.begin_a_gain.omokwang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.begin_a_gain.omokwang.ui.theme.OmokwangTheme
import com.begin_a_gain.signin.signin.SignInScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmokwangTheme {
                SignInScreen()
            }
        }
    }
}