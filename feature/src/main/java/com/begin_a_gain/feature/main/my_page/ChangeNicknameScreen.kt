package com.begin_a_gain.feature.main.my_page

import androidx.compose.runtime.Composable
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.feature.common.NicknameSettingContent
import com.begin_a_gain.model.type.common.ValidationState

@Composable
fun ChangeNicknameScreen() {
    OScreen(
        title = "닉네임 변경",
        bottomButtonText = "변경하기",
        bottomButtonType = ButtonType.Primary,
        onBottomButtonClick = {

        }
    ) {
        NicknameSettingContent(
            nickname = "",
            nicknameValidation = ValidationState.Normal,
            nicknameFailCase = null
        ) {

        }
    }
}