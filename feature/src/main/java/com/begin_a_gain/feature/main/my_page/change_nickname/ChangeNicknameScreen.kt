package com.begin_a_gain.feature.main.my_page.change_nickname

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.dialog.OFullPopup
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.feature.common.NicknameSettingContent
import com.begin_a_gain.model.type.common.ValidationState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ChangeNicknameFullPopup(
    nickname: String,
    viewModel: ChangeNickNameViewModel = hiltViewModel(),
    onDismissRequest: (isSaved: Boolean) -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    var isChanged by remember { mutableStateOf(false) }

    LaunchedEffect(nickname) {
        viewModel.setNickname(nickname)
    }

    LaunchedEffect(nickname) {
        snapshotFlow { state.nickname }
            .collect { currentInput ->
                isChanged = (currentInput != nickname) && currentInput.isNotEmpty()
            }
    }

    viewModel.collectSideEffect {
        when (it) {
            is ChangeNicknameSideEffect.SuccessToChange -> {
                onDismissRequest(true)
            }
        }
    }

    OFullPopup(
        onDismissRequest = {
            onDismissRequest(false)
        }
    ) {
        OScreen(
            title = "닉네임 변경",
            bottomButtonText = "변경하기",
            bottomButtonType = if (isChanged && state.nicknameValidation == ValidationState.Success) {
                ButtonType.Primary
            } else {
                ButtonType.Disable
            },
            onBottomButtonClick = {
                viewModel.saveNickname()
            },
            onBackButtonClick = {
                onDismissRequest(false)
            }
        ) {
            NicknameSettingContent(
                nickname = state.nickname,
                nicknameValidation = state.nicknameValidation,
                nicknameFailCase = state.nicknameFailCase
            ) {
                viewModel.setNickname(it)
            }
        }
    }
}