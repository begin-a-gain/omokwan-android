package com.begin_a_gain.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color

@Composable
fun OVerticalDivider(
    colorToken: ColorToken,
    modifier: Modifier = Modifier,
    height: Dp = 1.dp
) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .background(color = colorToken.color())
        .height(height))
}

@Composable
fun OHorizontalDivider(
    colorToken: ColorToken,
    modifier: Modifier = Modifier,
    width: Dp = 1.dp
) {
    Spacer(modifier = modifier
        .fillMaxHeight()
        .background(color = colorToken.color())
        .width(width))
}