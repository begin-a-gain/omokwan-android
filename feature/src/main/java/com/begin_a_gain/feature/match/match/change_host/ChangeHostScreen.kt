package com.begin_a_gain.feature.match.match.change_host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.OHorizontalDivider
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.selection.ORadioButton
import com.begin_a_gain.design.component.text.InitialText
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.ScreenBottomButtonType
import com.begin_a_gain.domain.model.ParticipantInfo
import com.begin_a_gain.feature.match.match.MatchSharedViewModel

@Preview
@Composable
fun ChangeHostScreen(
    matchId: Int = -1,
    viewModel: ChangeHostViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit = {}
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(matchId = matchId, participants = sharedViewModel.currentParticipants.value)
    }

    OScreen(
        title = "대국장 변경하기",
        showBackButton = true,
        onBackButtonClick = {
            navigateToSetting()
        },
        bottomButtonUiType = ScreenBottomButtonType.Modal,
        bottomButtonText = "대국장 변경하기",
        bottomButtonType = if (state.selectedIndex == -1) ButtonType.Disable else ButtonType.Primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.participants.forEachIndexed { index, member ->
                HostCandidateItem(
                    member = member,
                    isSelected = index == state.selectedIndex
                ) {
                    viewModel.setSelectedIndex(index)
                }
            }
        }
    }
}

@Preview
@Composable
fun HostCandidateItem(
    member: ParticipantInfo = ParticipantInfo(-1, "가나다라", 5, 5, 5),
    isSelected: Boolean = false,
    onSelect: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelect() }
            .background(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) ColorToken.UI_PRIMARY
                    .color()
                    .copy(alpha = 0.1f)
                else ColorToken.UI_BG.color()
            )
            .border(
                width = 1.dp,
                color = if (isSelected) ColorToken.STROKE_PRIMARY.color() else ColorToken.STROKE_01.color(),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialText(
            text = member.name,
            itemWidth = 58.dp,
            backgroundColor = ColorToken.UI_03.color(),
            isClickable = false
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            OText(text = member.name, style = OTextStyle.Subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OText(text = "콤보 ${member.combo}", style = OTextStyle.Subtitle1)
                OHorizontalDivider(colorToken = ColorToken.STROKE_02, modifier = Modifier.height(12.dp))
                OText(text = "오목알 ${member.omok}", style = OTextStyle.Subtitle1)
                OHorizontalDivider(colorToken = ColorToken.STROKE_02, modifier = Modifier.height(12.dp))
                OText(text = "대국 +${member.days}일 째", style = OTextStyle.Subtitle1)
            }
        }

        ORadioButton(
            modifier = Modifier.size(20.dp),
            checked = isSelected
        ) { }
    }
}