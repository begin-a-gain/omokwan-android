package com.begin_a_gain.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.OTextStyle

@Composable
fun OmokListScreen() {
    Column {
        OText(text = "Omok List", style = OTextStyle.Title1)
    }
}