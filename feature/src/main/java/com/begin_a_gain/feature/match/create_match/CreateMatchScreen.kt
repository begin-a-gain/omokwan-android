package com.begin_a_gain.feature.match.create_match

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingCommonLayout
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiState
import com.begin_a_gain.feature.match.create_match.util.ui.CreateMatchDialog
import com.begin_a_gain.feature.match.create_match.util.ui.LeaveCreateMatchDialog
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.dialog.ProgressBar
import com.begin_a_gain.design.util.OScreen
import org.orbitmvi.orbit.compose.collectSideEffect

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CreateMatchScreen(
    viewModel: CreateMatchViewModel,
    navigateToMain: () -> Unit,
    navigateToMatch: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val isMatchCreatable by viewModel.isMatchCreatable.collectAsStateWithLifecycle(initialValue = false)

    var showCreateMatchDialog by rememberSaveable { mutableStateOf(false) }
    var showLeaveWithoutSavingDialog by rememberSaveable {
        mutableStateOf(false)
    }

    viewModel.collectSideEffect {
        when(it) {
            CreateMatchSideEffect.CreateSuccess -> {
                navigateToMatch()
            }
        }
    }

    OScreen(
        title = "대국 만들기",
        bottomButtonText = "대국 시작하기",
        bottomButtonType = if (isMatchCreatable) ButtonType.Primary else ButtonType.Disable,
        onBottomButtonClick = {
            showCreateMatchDialog = true
        },
        onBackButtonClick = {
            showLeaveWithoutSavingDialog = true
        }
    ) {
        MatchSettingCommonLayout(
            state = MatchSettingUiState(
                title = state.title,
                setMatchTitle = viewModel::setMatchTitle,
                selectedRepeatDayType = state.selectedRepeatDayType,
                setRepeatDayType = viewModel::setRepeatDayType,
                setRepeatDay = viewModel::setRepeatDay,
                selectedDay = state.selectedDay,
                maxParticipantsCount = state.maxParticipantsCount,
                setMaximumParticipants = viewModel::setMaximumParticipants,
                selectedCategory = state.selectedCategory,
                setCategory = viewModel::setCategory,
                alarmOn = state.alarmOn,
                setAlarmOn = viewModel::setAlarmOn,
                alarmHour = state.alarmHour,
                alarmMin = state.alarmMin,
                isPrivate = state.isPrivate,
                setPrivate = viewModel::setPrivate,
                code = state.code
            )
        )

        if (showCreateMatchDialog) {
            CreateMatchDialog(
                matchTitle = state.title,
                onConfirmClick = {
                    showCreateMatchDialog = false
                    viewModel.createMatch()
                }
            ) {
                showCreateMatchDialog = false
            }
        }

        if (showLeaveWithoutSavingDialog) {
            LeaveCreateMatchDialog(
                onConfirmClick = navigateToMain
            ) {
                showLeaveWithoutSavingDialog = false
            }
        }

        if (state.loadingCount != 0) {
            ProgressBar()
        }
    }
}