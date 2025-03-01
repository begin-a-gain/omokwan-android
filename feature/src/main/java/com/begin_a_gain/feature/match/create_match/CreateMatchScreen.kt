package com.begin_a_gain.feature.match.create_match

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.MatchCategoryGrid
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType
import com.begin_a_gain.feature.match.create_match.util.ui.CodeTextField
import com.begin_a_gain.feature.match.create_match.util.ui.MatchCodeDialog
import com.begin_a_gain.library.design.component.ODivider
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.bottom_sheet.OPickerBottomSheet
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.dialog.ODialog
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.selection.OSwitch
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.component.text.OTextField
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.begin_a_gain.library.design.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateMatchScreen(
    viewModel: CreateMatchViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val bottomSheetState = rememberModalBottomSheetState()
    var showRepeatDayTypePicker by rememberSaveable { mutableStateOf(false) }
    var showMaxParticipantsPicker by rememberSaveable { mutableStateOf(false) }
    var showCategoryBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCodeDialog by rememberSaveable { mutableStateOf(false) }

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
                text = state.title
            ) {
                viewModel.setMatchTitle(it)
            }

            SettingBox(
                modifier = Modifier.fillMaxWidth(),
                label = "기본 설정"
            ) {
                SettingRow(
                    title = "반복 요일",
                    value = state.selectedRepeatDayType.title
                ) {
                    showRepeatDayTypePicker = true
                }
                AnimatedVisibility(
                    visible = state.selectedRepeatDayType == RepeatDayType.SpecificDay,
                    enter = fadeIn() + expandIn(),
                    exit = shrinkOut() + fadeOut()
                ) {
                    DaySelection(
                        selectedDays = state.selectedDay
                    ) { index ->
                        viewModel.setRepeatDay(index)
                    }
                }

                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "최대 인원 수",
                    value = "${state.maxParticipantsCount}"
                ) {
                    showMaxParticipantsPicker = true
                }
            }

            SettingBox(
                modifier = Modifier.fillMaxWidth(),
                label = "기타 설정"
            ) {
                SettingRow(
                    title = "대국 카테고리",
                    value = ""
                ) {
                    showCategoryBottomSheet = true
                }
                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "리마인드 알림",
                    value = if (state.alarmOn) "" else "",
                    showSwitch = true,
                    switchChecked = false,
                    onCheckedChanged = {
                        // Todo : add permission
                        viewModel.setAlarmOn(!state.alarmOn)
                    }
                ) { }
                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "비공개",
                    value = if (state.isPrivate) "코드 : ${state.code}" else "",
                    showSwitch = true,
                    switchChecked = state.isPrivate,
                    onCheckedChanged = {
                        if (state.isPrivate) {
                            viewModel.setPrivate(false)
                        } else {
                            showCodeDialog = true
                        }
                    }
                ) {}
            }
        }

        if (showRepeatDayTypePicker) {
            OPickerBottomSheet(
                title = "반복 요일",
                items = RepeatDayType.entries.map { it.title },
                selectedIndex = RepeatDayType.entries.indexOf(state.selectedRepeatDayType),
                sheetState = bottomSheetState,
                onDismissRequest = { showRepeatDayTypePicker = false }
            ) {
                viewModel.setRepeatDayType(RepeatDayType.entries[it])
            }
        }

        if (showMaxParticipantsPicker) {
            OPickerBottomSheet(
                title = "최대 인원 수",
                items = (1..5).map { "$it" },
                selectedIndex = state.maxParticipantsCount - 1,
                sheetState = bottomSheetState,
                onDismissRequest = { showMaxParticipantsPicker = false }
            ) {
                viewModel.setMaximumParticipants(it + 1)
            }
        }

        if (showCategoryBottomSheet) {
            CategoryBottomSheet(
                sheetState = bottomSheetState,
                selectedIndex = state.selectedCategoryIndex,
                onDismissRequest = { showCategoryBottomSheet = false },
                onSelected = {
                    viewModel.setCategory(it)
                }
            )
        }

        if (showCodeDialog) {
            MatchCodeDialog(
                onConfirmClick = { code ->
                    viewModel.setPrivate(true, code)
                },
                onCancelClick = { showCodeDialog = false }
            ) {
                showCodeDialog = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    sheetState: SheetState,
    selectedIndex: Int,
    onDismissRequest: () -> Unit,
    onSelected: (Int) -> Unit
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(selectedIndex) }

    OBottomSheet(
        title = "대국 카테고리",
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            MatchCategoryGrid(selectedIndex) {
                currentIndex = it
            }
            Spacer(modifier = Modifier.weight(1f))
            OButton(
                modifier = Modifier.fillMaxWidth(),
                text = "확인"
            ) {
                onSelected(currentIndex)
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