package com.begin_a_gain.feature.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.util.OScreen

@Preview
@Composable
fun SignUpDoneScreen(
    navigateToMain: () -> Unit = {}
) {
    OScreen(
        showTitle = false,
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
            Row(modifier = Modifier.fillMaxWidth()) {
                OImage(
                    image = OImageRes.TypoSignUp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(280.dp, 150.dp)
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(60.dp))
            OImage(
                image = OImageRes.OmogiSignUp,
                modifier = Modifier
                    .size(310.dp, 380.dp)
            )
        }
    }
}