package com.begin_a_gain.design.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.begin_a_gain.design.theme.ColorToken.Companion.getDarkColor
import com.begin_a_gain.design.theme.ColorToken.Companion.getLightColor

val LocalDarkMode = compositionLocalOf { false }

@Composable
@ReadOnlyComposable
fun isDarkMode() = LocalDarkMode.current

@Composable
fun OmokwanTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val osBarColor = if (darkTheme) {
                ColorToken.UI_BG.getDarkColor().toArgb()
            } else {
                ColorToken.UI_BG.getLightColor().toArgb()
            }
            window.statusBarColor = osBarColor
            window.navigationBarColor = osBarColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        content = {
            CompositionLocalProvider(
                LocalDarkMode provides darkTheme
            ) {
                content()
            }
        }
    )
}