package com.begin_a_gain.omokwang

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.begin_a_gain.design.theme.OmokwangTheme
import com.begin_a_gain.omokwang.navigation.OmokwanGraph
import com.begin_a_gain.omokwang.navigation.SignIn
import com.begin_a_gain.omokwang.navigation.Splash
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OmokwangTheme(
                darkTheme = false
            ) {
                val navController = rememberNavController()
                OmokwanGraph(navController = navController, startDestination = Splash)
            }
        }
    }
}