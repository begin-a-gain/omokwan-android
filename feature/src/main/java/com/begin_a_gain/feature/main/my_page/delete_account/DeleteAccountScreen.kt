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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.dialog.OFullPopup
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.selection.OCheckBox
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.OTextField
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.noRippleClickable

@Preview
@Composable
fun DeleteAccountFullPopup(
    viewModel: DeleteAccountViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit = {}
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    OFullPopup(
        onDismissRequest = onDismissRequest
    ) {
        OScreen(
            title = "회원 탈퇴"
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

                listOf(
                    "자주 사용하지 않아요.",
                    "원하는 기능이 없어요.",
                    "쓰기가 복잡해요.",
                    "다른 앱을 쓰고 있어요.",
                    "기타 (직접 입력)"
                ).forEachIndexed { index, text ->
                    Column {
                        Row(
                            modifier = Modifier.noRippleClickable {
                                viewModel.selectReason(index)
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OCheckBox (
                                checked = index in state.reasons
                            ) {
                                viewModel.selectReason(index)
                            }
                            Spacer(Modifier.width(8.dp))
                            OText(
                                text = text,
                                style = OTextStyle.Subtitle3
                            )
                        }

                        if (index == 4 && 4 in state.reasons) {
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
    }
}