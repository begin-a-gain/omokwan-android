package com.begin_a_gain.library.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color

@Composable
fun ODivider(
    colorToken: ColorToken,
    modifier: Modifier = Modifier,
    height: Dp = 1.dp
) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .background(color = colorToken.color())
        .height(height))
}