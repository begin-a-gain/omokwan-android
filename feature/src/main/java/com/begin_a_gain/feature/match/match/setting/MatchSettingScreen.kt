package com.begin_a_gain.feature.match.match.setting

import android.content.ClipData
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.OVerticalDivider
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.dialog.ODialog
import com.begin_a_gain.design.component.dialog.ProgressBar
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.ScreenBottomButtonType
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingCommonLayout
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiState
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiType
import com.begin_a_gain.feature.match.common.match_setting.SettingBox
import com.begin_a_gain.feature.match.common.match_setting.SettingRow
import com.begin_a_gain.feature.match.match.MatchSharedViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Preview
@Composable
fun MatchSettingScreen(
    matchId: Int = 0,
    viewModel: MatchSettingViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel(),
    navigateToMatch: () -> Unit = {},
    navigateToInvite: () -> Unit = {},
    navigateToChangeHost: () -> Unit = {}
) {
    val scroll = rememberScrollState()
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    var showCheckLeavingDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initialize(matchId, sharedViewModel.isHost.value)
        snapshotFlow { state.currentSettings }
            .distinctUntilChanged()
            .collect { settings ->
                viewModel.intent {
                    reduce {
                        state.copy(
                            hasChanges = settings != state.initialSettings
                        )
                    }
                }
            }
    }

    OScreen(
        title = "대국 설정",
        showBackButton = false,
        trailingIcon = OImageRes.Cancel,
        onTrailingIconClick = {
            navigateToMatch()
        },
        bottomButtonUiType = ScreenBottomButtonType.Modal,
        bottomButtonText = "저장하기",
        bottomButtonType = if (state.hasChanges) ButtonType.Primary else ButtonType.Disable
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {
            MatchSettingCommonLayout(
                type = if (state.isHost) MatchSettingUiType.MatchHost else MatchSettingUiType.MatchMember,
                state = MatchSettingUiState(
                    title = state.currentSettings.title,
                    setMatchTitle = { title ->
                        viewModel.setTitle(title)
                    },
                    daysInProgress = state.daysInProgress,
                    matchCode = state.matchCode,
                    onClickMatchCode = {
                        scope.launch {
                            val clipData = ClipData.newPlainText("match_code", state.matchCode)
                            clipboard.setClipEntry(clipData.toClipEntry())
                        }
                    },
                    selectedDay = (1..7).map { true },
                    maxParticipantsCount = state.currentSettings.maxParticipantsCount,
                    setMaximumParticipants = { count ->
                        viewModel.setMaxParticipantsCount(count)
                    },
                    selectedCategory = state.currentSettings.selectedCategory,
                    setCategory = { category ->
                        viewModel.setCategory(category)
                    },
                    isPrivate = state.currentSettings.isPrivate,
                    setPrivate = { value, code ->
                        viewModel.setPrivate(value, code)
                    },
                    password = state.currentSettings.password,
                    onPasswordClick = {
                        scope.launch {
                            val clipData = ClipData.newPlainText("password", state.currentSettings.password)
                            clipboard.setClipEntry(clipData.toClipEntry())
                        }
                    }
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

                if (state.isHost) {
                    OVerticalDivider(colorToken = ColorToken.STROKE_02)
                    SettingRow(
                        title = "방장 변경하기",
                        value = ""
                    ) {
                        navigateToChangeHost()
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

        if (state.isLoading) {
            ProgressBar()
        }
    }
}