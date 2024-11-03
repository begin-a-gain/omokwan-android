package com.begin_a_gain.feature.sign_up

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.library.core.util.ValidationState
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.component.text.OTextField
import com.begin_a_gain.library.design.component.text.TextFieldStatus
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    OScreen(
        onBackButtonClick = {
            // Todo
        },
        bottomButtonText = "다음",
        bottomButtonType = when (state.nicknameValidation) {
            ValidationState.Success -> ButtonType.Primary
            else -> ButtonType.Disable
        },
        onBottomButtonClick = {
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
        OTextField(
            text = state.nickname,
            hint = "ex. 오목완",
            maxCount = 10,
            message = state.nicknameFailCase?.message,
            status = when (state.nicknameValidation) {
                ValidationState.Normal,
                ValidationState.Success -> TextFieldStatus.Default

                else -> TextFieldStatus.Error
            }
        ) {
            viewModel.setNickname(it)
        }
    }
}