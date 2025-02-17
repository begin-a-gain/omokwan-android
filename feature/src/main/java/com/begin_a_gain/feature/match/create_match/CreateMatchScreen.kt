package com.begin_a_gain.feature.match.create_match

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.ODivider
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.selection.OSwitch
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.component.text.OTextField
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun CreateMatchScreen() {
    OScreen(
        title = "대국 만들기",
        bottomButtonText = "대국 시작하기",
        bottomButtonType = ButtonType.Disable,
        onBottomButtonClick = {

        }
    ) {
        Column(
            Modifier.padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OTextField(
                label = "대국 이름",
                hint = "대국 이름을 적어주세요.",
                text = ""
            ) {

            }

            SettingBox(
                modifier = Modifier.fillMaxWidth(),
                label = "기본 설정"
            ) {
                SettingRow(
                    title = "반복 요일",
                    value = "직접 선택"
                ) {
                    
                }
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandIn(),
                    exit = shrinkOut() + fadeOut()
                ) {
                    DaySelection(
                        selectedDays = listOf(0, 1)
                    ) {

                    }
                }

                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "최대 인원 수",
                    value = "5"
                ) {

                }
            }

            SettingBox(
                modifier = Modifier.fillMaxWidth(),
                label = "기타 설정"
            ) {
                SettingRow(
                    title = "대국 카테고리",
                    value = "운동"
                ) {

                }
                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "리마인드 알림",
                    value = "",
                    showSwitch = true,
                    switchChecked = false,
                    onCheckedChanged = {

                    }
                ) {

                }
                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "비공개",
                    value = "",
                    showSwitch = true,
                    switchChecked = false,
                    onCheckedChanged = {

                    }
                ) {

                }
            }
        }
    }
}

@Composable
fun SettingBox(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        OText(
            modifier = Modifier.padding(start = 16.dp),
            text = label,
            style = OTextStyle.Subtitle2
        )
        Spacer(modifier = Modifier.height(6.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = ColorToken.STROKE_02.color(),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            content()
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    value: String,
    showSwitch: Boolean = false,
    switchChecked: Boolean = false,
    onCheckedChanged: () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() }
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            OText(text = title, style = OTextStyle.Subtitle3)
            Spacer(modifier = Modifier.weight(1f))
            OText(text = value, style = OTextStyle.Body2, color = ColorToken.TEXT_02)
            if (!showSwitch) {
                Spacer(modifier = Modifier.width(8.dp))
                OImage(image = OImageRes.ArrowRight, size = 14.dp)
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        if (showSwitch) {
            OSwitch(checked = switchChecked) {
                onCheckedChanged()
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun DaySelection(
    selectedDays: List<Int> = listOf(0, 1),
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
    ) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEachIndexed { index, day ->
            val selected = selectedDays.contains(index)
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

@Preview
@Composable
fun SettingRowPreview() {
    OScreen {
        SettingRow("Test1", "Value1", false, false, {}, {})
        SettingRow("Test2", "Value2", true, true, {}, {})
        SettingRow("Test3", "", true, false, {}, {})
        SettingRow("Test4", "Value4", false, true, {}, {})
    }
}