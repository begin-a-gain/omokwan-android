package com.begin_a_gain.feature.sign_up

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen

@Composable
fun SignUpScreen() {
    OScreen(
        onClickBackButton = {
            // Todo
        }
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        OText(text = "닉네임을 설정해주세요.", style = OTextStyle.Display)
        Spacer(modifier = Modifier.height(16.dp))
        OText(
            text = "2~10글자 사이의 한글, 영문, 숫자로 입력해주세요.",
            style = OTextStyle.Body2,
            color = ColorToken.TEXT_02
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Todo update textfield
    }
}