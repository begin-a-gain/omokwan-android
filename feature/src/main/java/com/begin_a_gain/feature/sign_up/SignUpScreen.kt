package com.begin_a_gain.feature.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.model.type.common.ValidationState
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.dialog.ProgressBar
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.OTextField
import com.begin_a_gain.design.component.text.TextFieldStatus
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.feature.common.NicknameSettingContent
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignUpScreen(
    navigateToSignUpDone: () -> Unit,
    popBack: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    OScreen(
        onBackButtonClick = {
            popBack()
        },
        bottomButtonText = "다음",
        bottomButtonType = when (state.nicknameValidation) {
            ValidationState.Success -> ButtonType.Primary
            else -> ButtonType.Disable
        },
        onBottomButtonClick = {
            viewModel.saveNickname()
        }
    ) {
        NicknameSettingContent(
            nickname = state.nickname,
            nicknameValidation = state.nicknameValidation,
            nicknameFailCase = state.nicknameFailCase
        ) {
            viewModel.setNickname(it)
        }

        if (state.isLoading) {
            ProgressBar()
        }
    }

    viewModel.collectSideEffect {
        when(it) {
            is SignUpSideEffect.SignUpSuccess -> {
                navigateToSignUpDone()
            }

            is SignUpSideEffect.NavigateToSignIn -> {
                popBack()
            }
        }
    }
}