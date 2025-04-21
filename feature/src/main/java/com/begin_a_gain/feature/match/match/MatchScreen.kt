package com.begin_a_gain.feature.match.match

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun MatchScreen() {
    OScreen(
        title = "대국방 이름",
        showBackButton = true,
        trailingIcon = OImageRes.Menu,
        onTrailingIconClick = {
            // Todo : navigate to menu screen
        }
    ) {


    }
}
