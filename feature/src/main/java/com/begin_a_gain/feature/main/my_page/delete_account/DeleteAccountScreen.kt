package com.begin_a_gain.feature.main.my_page.delete_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.dialog.ODialog
import com.begin_a_gain.design.component.dialog.OFullPopup
import com.begin_a_gain.design.component.dialog.ProgressBar
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.selection.OCheckBox
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.OTextField
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.noRippleClickable
import org.orbitmvi.orbit.compose.collectSideEffect

@Preview
@Composable
fun DeleteAccountFullPopup(
    viewModel: DeleteAccountViewModel = hiltViewModel(),
    onDismissRequest: (isDeleted: Boolean) -> Unit = {}
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }

    viewModel.collectSideEffect {
        when (it) {
            is DeleteAccountSideEffect -> {
                showSuccessDialog = true
            }
        }
    }

    OFullPopup(
        onDismissRequest = {
            onDismissRequest(false)
        }
    ) {
        OScreen(
            title = "회원 탈퇴",
            bottomButtonText = "탈퇴하기",
            bottomButtonType = if (state.reasons.isNotEmpty() &&
                (!state.isSelectedOther() || state.otherReason.isNotBlank())) {
                ButtonType.Primary
            } else ButtonType.Disable,
            onBottomButtonClick = {
                viewModel.deleteAccount()
            }
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                OText(
                    text = "탈퇴 이유를 알려주세요.\n오목완이 더 나은 서비스가 되는데 많은 도움이 될거에요.",
                    style = OTextStyle.Display
                )

                OText(
                    text = "최소 1개 이상 선택해주세요.",
                    style = OTextStyle.Body2,
                    color = ColorToken.TEXT_02
                )

                DeleteAccountReason.entries.forEach { reason ->
                    Column {
                        Row(
                            modifier = Modifier.noRippleClickable {
                                viewModel.selectReason(reason)
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OCheckBox (
                                checked = reason in state.reasons
                            ) {
                                viewModel.selectReason(reason)
                            }
                            Spacer(Modifier.width(8.dp))
                            OText(
                                text = reason.message,
                                style = OTextStyle.Subtitle3
                            )
                        }

                        if (reason == DeleteAccountReason.OTHER && state.isSelectedOther()) {
                            Spacer(Modifier.height(10.dp))
                            OTextField(
                                text = state.otherReason,
                                trailingIcon = if (state.otherReason.isNotBlank()) OImageRes.Cancel else null,
                                trailingIconColor = ColorToken.ICON_01,
                                onTrailingIconClick = {
                                    viewModel.setOtherReason("")
                                }
                            ) {
                                viewModel.setOtherReason(it)
                            }
                        }
                    }
                }
            }
        }

        if (showSuccessDialog) {
            ODialog(
                title = "탈퇴가 완료되었어요.",
                message = "언젠가 다시 도전해봐요. 기다릴게요!",
                buttonText = "확인",
                onButtonClick = {
                    showSuccessDialog = false
                    onDismissRequest(true)
                }
            ) {
                showSuccessDialog = false
                onDismissRequest(true)
            }
        }
        
        if (state.isLoading) {
            ProgressBar()
        }
    }
}