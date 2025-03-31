package com.begin_a_gain.feature.match.create_match.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle

@Preview
@Composable
fun DaySelection(
    selectedDays: List<Boolean> = (1..7).map { false },
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
    ) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEachIndexed { index, day ->
            val selected = selectedDays[index]
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = if (selected) ColorToken.STROKE_PRIMARY.color() else ColorToken.STROKE_02.color()
                    )
                    .background(
                        if (selected) ColorToken.UI_PRIMARY.color() else ColorToken.UI_BG.color()
                    )
                    .clickable {
                        onClick(index)
                    },
                contentAlignment = Alignment.Center
            ) {
                OText(
                    text = day,
                    style = OTextStyle.Subtitle2,
                    color = if (selected) ColorToken.TEXT_ON_01 else ColorToken.TEXT_02
                )
            }
            if (index < 6) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}