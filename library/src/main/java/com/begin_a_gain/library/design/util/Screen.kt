package com.begin_a_gain.library.design.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color

val ScreenHorizontalPadding = 20.dp

@Composable
fun Modifier.initScreen(usePadding: Boolean = true) = this
    .fillMaxSize()
    .background(ColorToken.UI_BG.color())
    .padding(horizontal = if (usePadding) ScreenHorizontalPadding else 0.dp)