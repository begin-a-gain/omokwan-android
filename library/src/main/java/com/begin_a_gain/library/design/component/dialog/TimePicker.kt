package com.begin_a_gain.library.design.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun OTimePickerDialog(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    onSelected: (Int, Int) -> Unit = { _, _ -> },
    onDismissRequest: () -> Unit = {}
) {
    val state = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = false
    )

    ODialog(
        title = "리마인드 알림",
        buttonText = "선택",
        onButtonClick = {
            onSelected(state.hour, state.minute)
            onDismissRequest()
        },
        additionalButtonText = "취소",
        onAdditionalButtonClick = onDismissRequest,
        content = {
            Column {
                TimePicker(
                    state = state,
                    colors = TimePickerDefaults.colors().copy(
                        containerColor = ColorToken.UI_BG.color(),
                        clockDialColor = ColorToken.UI_BG.color(),
                        clockDialSelectedContentColor = ColorToken.TEXT_ON_01.color(),
                        clockDialUnselectedContentColor = ColorToken.TEXT_01.color(),
                        selectorColor = ColorToken.UI_PRIMARY.color(),
                        periodSelectorBorderColor = ColorToken.UI_PRIMARY.color(),
                        periodSelectorSelectedContainerColor = ColorToken.UI_PRIMARY.color()
                            .copy(alpha = 0.7f),
                        periodSelectorUnselectedContainerColor = ColorToken.UI_BG.color(),
                        periodSelectorSelectedContentColor = ColorToken.TEXT_ON_01.color(),
                        periodSelectorUnselectedContentColor = ColorToken.TEXT_01.color(),
                        timeSelectorSelectedContainerColor = ColorToken.UI_BG.color(),
                        timeSelectorUnselectedContainerColor = ColorToken.UI_BG.color(),
                        timeSelectorSelectedContentColor = ColorToken.UI_PRIMARY.color(),
                        timeSelectorUnselectedContentColor = ColorToken.TEXT_01.color()
                    )
                )
            }
        }
    ) {
        onDismissRequest()
    }
}