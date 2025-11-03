package com.begin_a_gain.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.OTextStyle

@Composable
fun MyPageScreen() {
    Column {
        OText(text = "My Page", style = OTextStyle.Title1)
    }
}