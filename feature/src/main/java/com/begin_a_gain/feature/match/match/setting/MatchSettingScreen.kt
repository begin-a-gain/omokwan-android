package com.begin_a_gain.feature.match.match.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun MatchSettingScreen() {
    OScreen(
        title = "대국 설정",
        showBackButton = false,
        trailingIcon = OImageRes.Cancel,
        onTrailingIconClick = {
            // Todo : dismiss
        }
    ) {

    }
}