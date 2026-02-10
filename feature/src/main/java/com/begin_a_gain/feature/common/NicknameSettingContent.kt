package com.begin_a_gain.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.OTextField
import com.begin_a_gain.design.component.text.TextFieldStatus
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.feature.sign_up.NicknameFailCase
import com.begin_a_gain.model.type.common.ValidationState


@Composable
fun NicknameSettingContent(
    nickname: String,
    nicknameValidation: ValidationState,
    nicknameFailCase: NicknameFailCase?,
    onNicknameChange: (String) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        OText(text = "닉네임을 설정해주세요.", style = OTextStyle.Display)
        Spacer(modifier = Modifier.height(16.dp))
        OText(
            text = "2~10글자 사이의 한글, 영문, 숫자로 입력해주세요.",
            style = OTextStyle.Body2,
            color = ColorToken.TEXT_02
        )
        Spacer(modifier = Modifier.height(24.dp))
        OTextField(
            text = nickname,
            hint = "ex. 오목완",
            maxCount = 10,
            message = if (nicknameValidation == ValidationState.Fail) {
                nicknameFailCase?.message
            } else "",
            status = when (nicknameValidation) {
                ValidationState.Normal,
                ValidationState.Success -> TextFieldStatus.Default

                else -> TextFieldStatus.Error
            }
        ) {
            onNicknameChange(it)
        }
    }
}