package com.begin_a_gain.feature.match.match.change_leader

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.domain.model.MemberHistory
import com.begin_a_gain.library.design.component.OHorizontalDivider
import com.begin_a_gain.library.design.component.selection.ORadioButton
import com.begin_a_gain.library.design.component.text.InitialText
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.begin_a_gain.library.design.util.ScreenBottomButtonType

@Preview
@Composable
fun ChangeLeaderScreen() {
    OScreen(
        title = "대국장 변경하기",
        showBackButton = true,
        bottomButtonUiType = ScreenBottomButtonType.Modal,
        bottomButtonText = "대국장 변경하기"
    ) {
        var selectedIndex by rememberSaveable {
            mutableStateOf(0)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf(
                MemberHistory("", "연날리기", 1000, 100, 1000),
                MemberHistory("", "생갈치1호의행방불명", 1, 10, 10),
                MemberHistory("", "쥬짱", 10, 5, 10),
            ).forEachIndexed { index, member ->
                LeaderCandidateItem(
                    member = member,
                    isSelected = index == selectedIndex
                ) {
                    selectedIndex = index
                }
            }
        }
    }
}

@Preview
@Composable
fun LeaderCandidateItem(
    member: MemberHistory = MemberHistory("", "가나다라", 5, 5, 5),
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