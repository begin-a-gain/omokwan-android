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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingCommonLayout
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiState
import com.begin_a_gain.feature.match.common.match_setting.MatchSettingUiType
import com.begin_a_gain.feature.match.common.match_setting.SettingBox
import com.begin_a_gain.feature.match.common.match_setting.SettingRow
import com.begin_a_gain.library.design.component.OVerticalDivider
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun MatchSettingScreen(
    isLeader: Boolean = false,
) {
    val scroll = rememberScrollState()

    OScreen(
        title = "대국 설정",
        showBackButton = false,
        trailingIcon = OImageRes.Cancel,
        onTrailingIconClick = {
            // Todo : dismiss
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
                    title = "",
                    setMatchTitle = {

                    },
                    daysInProgress = 1,
                    selectedDay = (1..7).map { true },
                    maxParticipantsCount = 5,
                    setMaximumParticipants = {

                    },
                    selectedCategoryIndex = 1,
                    setCategory = {

                    },
                    alarmOn = false,
                    alarmHour = 0,
                    alarmMin = 0,
                    isPrivate = false,
                    code = "0000"
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
                }
                if (isLeader) {
                    OVerticalDivider(colorToken = ColorToken.STROKE_02)
                    SettingRow(
                        title = "방장 변경하기",
                        value = ""
                    ) {
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
                
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}