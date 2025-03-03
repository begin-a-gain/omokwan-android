package com.begin_a_gain.feature.match.create_match

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.CategoryBottomSheet
import com.begin_a_gain.feature.match.create_match.util.constant.categories
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType
import com.begin_a_gain.feature.match.create_match.util.ui.DaySelection
import com.begin_a_gain.feature.match.create_match.util.ui.MatchCodeDialog
import com.begin_a_gain.feature.match.create_match.util.ui.NotificationPermissionBottomSheet
import com.begin_a_gain.library.design.component.ODivider
import com.begin_a_gain.library.design.component.bottom_sheet.OPickerBottomSheet
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.dialog.OTimePickerDialog
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.selection.OSwitch
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.component.text.OTextField
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreateMatchScreen(
    viewModel: CreateMatchViewModel,
    navigateToMain: () -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(true)
    var showRepeatDayTypePicker by rememberSaveable { mutableStateOf(false) }
    var showMaxParticipantsPicker by rememberSaveable { mutableStateOf(false) }
    var showCategoryBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCodeDialog by rememberSaveable { mutableStateOf(false) }

    val notificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    var showNotificationPermissionBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showNotificationTimeDialog by rememberSaveable { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (showNotificationPermissionBottomSheet) {
                    showNotificationPermissionBottomSheet = false
                    showNotificationTimeDialog = true
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    OScreen(
        title = "대국 만들기",
        bottomButtonText = "대국 시작하기",
        bottomButtonType = ButtonType.Disable,
        onBottomButtonClick = { },
        onBackButtonClick = navigateToMain
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
                    value = if (categories.getOrNull(state.selectedCategoryIndex) == null) ""
                    else categories[state.selectedCategoryIndex].second
                ) {
                    showCategoryBottomSheet = true
                }

                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "리마인드 알림",
                    value = if (state.alarmOn) {
                        if (state.alarmHour > 12) "오후 ${state.alarmHour - 12}:${state.alarmMin}"
                        else "오전 ${state.alarmHour}:${state.alarmMin}"
                    } else "",
                    showSwitch = true,
                    isRowClickable = state.alarmOn,
                    switchChecked = state.alarmOn,
                    onCheckedChanged = {
                        when {
                            notificationPermission.status.isGranted -> {
                                if (state.alarmOn) {
                                    viewModel.setAlarmOn(false)
                                } else {
                                    showNotificationTimeDialog = true
                                }
                            }

                            notificationPermission.status.shouldShowRationale -> {
                                showNotificationPermissionBottomSheet = true
                            }

                            else -> {
                                notificationPermission.launchPermissionRequest()
                            }
                        }
                    }
                ) {
                    if (state.alarmOn) {
                        showNotificationTimeDialog = true
                    }
                }

                ODivider(colorToken = ColorToken.STROKE_02)
                SettingRow(
                    title = "비공개",
                    value = if (state.isPrivate) "코드 : ${state.code}" else "",
                    showSwitch = true,
                    switchChecked = state.isPrivate,
                    isRowClickable = state.isPrivate,
                    onCheckedChanged = {
                        if (state.isPrivate) viewModel.setPrivate(false)
                        else showCodeDialog = true
                    }
                ) {
                    if (state.isPrivate) {
                        showCodeDialog = true
                    }
                }
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
                    showCategoryBottomSheet = false
                    viewModel.setCategory(it)
                }
            )
        }

        if (showCodeDialog) {
            MatchCodeDialog(
                code = state.code,
                onConfirmClick = { code ->
                    viewModel.setPrivate(true, code)
                },
                onCancelClick = { showCodeDialog = false }
            ) {
                showCodeDialog = false
            }
        }

        if (showNotificationPermissionBottomSheet) {
            NotificationPermissionBottomSheet(
                sheetState = bottomSheetState,
                onAgreeClick = {
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    }
                    context.startActivity(intent)
                }
            ) {
                showNotificationPermissionBottomSheet = false
            }
        }

        if (showNotificationTimeDialog) {
            OTimePickerDialog(
                title = "리마인드 알람",
                initialHour = state.alarmHour,
                initialMinute = state.alarmMin,
                onSelected = { hour, minute ->
                    viewModel.setAlarmOn(true, hour, minute)
                }
            ) {
                showNotificationTimeDialog = false
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
    isRowClickable: Boolean = true,
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
                .run {
                    if (isRowClickable) {
                        this.clickable { onClick() }
                    } else {
                        this
                    }
                }
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
fun SettingRowPreview() {
    OScreen {
        SettingRow("Test1", "Value1", false, true, false, {}, {})
        SettingRow("Test2", "Value2", true, true, true, {}, {})
        SettingRow("Test3", "", true, true, false, {}, {})
        SettingRow("Test4", "Value4", false, true, true, {}, {})
    }
}