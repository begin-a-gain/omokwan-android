package com.begin_a_gain.design.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(20.dp),
        color = ColorToken.STROKE_PRIMARY.color()
    )
}