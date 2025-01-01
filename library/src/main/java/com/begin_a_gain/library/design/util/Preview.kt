package com.begin_a_gain.library.design.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OPreview(
    modifier: Modifier = Modifier,
    verticalSpace: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ) {
        content()
    }
}