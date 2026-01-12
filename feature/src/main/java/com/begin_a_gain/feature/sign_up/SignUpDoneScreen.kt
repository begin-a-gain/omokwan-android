package com.begin_a_gain.feature.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.util.OScreen

@Composable
fun SignUpDoneScreen(
    navigateToMain: () -> Unit
) {
    OScreen(
        showBackButton = false,
        bottomButtonText = "오목완 시작하기",
        bottomButtonType = ButtonType.Primary,
        onBottomButtonClick = {
            navigateToMain()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .size(310.dp, 380.dp)
                    .background(ColorToken.UI_DISABLE_01.color())
            )
        }
    }
}