package com.begin_a_gain.feature.match.match.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingCommonLayout
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiState
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiType
import com.begin_a_gain.feature.match.common.match_setting.SettingBox
import com.begin_a_gain.feature.match.common.match_setting.SettingRow
import com.begin_a_gain.design.component.OVerticalDivider
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.dialog.ODialog
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.util.OScreen

@Preview
@Composable
fun MatchSettingScreen(
    matchId: Int = 0,
    viewModel: MatchSettingViewModel = hiltViewModel(),
    navigateToMatch: () -> Unit = {},
    navigateToInvite: () -> Unit = {},
    navigateToChangeLeader: () -> Unit = {}
) {
    val scroll = rememberScrollState()

    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    var isLeader by rememberSaveable { mutableStateOf(false) } // changed by sharedViewModel
    var showCheckLeavingDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initialize(matchId)
    }

    OScreen(
        title = "대국 설정",
        showBackButton = false,
        trailingIcon = OImageRes.Cancel,
        onTrailingIconClick = {

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {
            MatchSettingCommonLayout(
                type = if (isLeader) MatchSettingUiType.MatchLeader else MatchSettingUiType.MatchMember,
                state = MatchSettingUiState(
                    title = state.title,
                    setMatchTitle = { },
                    daysInProgress = state.daysInProgress,
                    matchCode = state.matchCode,
                    onClickMatchCode = {

                    },
                    selectedDay = (1..7).map { true },
                    maxParticipantsCount = state.maxParticipantsCount,
                    setMaximumParticipants = { },
                    selectedCategory = state.selectedCategory,
                    setCategory = {

                    },
                    isPrivate = state.isPrivate,
                    password = state.password
                )
            )

            SettingBox(
                modifier = Modifier.fillMaxWidth(),
                label = "대국 관리"
            ) {
                SettingRow(
                    title = "초대하기",
                    value = ""
                ) {
                    navigateToInvite()
                }

                if (isLeader) {
                    OVerticalDivider(colorToken = ColorToken.STROKE_02)
                    SettingRow(
                        title = "방장 변경하기",
                        value = ""
                    ) {
                        navigateToChangeLeader()
                    }
                }
            }

            OVerticalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                colorToken = ColorToken.STROKE_02
            )
            OButton(
                modifier = Modifier.fillMaxWidth(),
                type = ButtonType.Alert,
                text = "대국 나가기"
            ) {
                showCheckLeavingDialog = true
            }
            Spacer(modifier = Modifier.height(80.dp))
        }

        if (showCheckLeavingDialog) {
            ODialog(
                title = "대국에서 나가시겠습니까?",
                message = "대국에 대한 모든 정보가 사라지며 복구할 수 없습니다.",
                buttonText = "나가기",
                buttonType = ButtonType.Alert,
                onButtonClick = {
                    // Todo : update
                    showCheckLeavingDialog = false
                },
                additionalButtonText = "취소",
                onAdditionalButtonClick = {
                    showCheckLeavingDialog = false
                }
            ) {
                showCheckLeavingDialog = false
            }
        }
    }
}