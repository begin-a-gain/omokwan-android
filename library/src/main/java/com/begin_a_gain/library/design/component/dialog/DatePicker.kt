package com.begin_a_gain.library.design.component.dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.core.util.DateTimeUtil.isFutureThanThisYear
import com.begin_a_gain.library.core.util.DateTimeUtil.isFutureThanToday
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.noRippleClickable
import org.joda.time.DateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ODatePickerDialog(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onSelectDate: (DateTime) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            OText(
                modifier = Modifier
                    .noRippleClickable {
                        onSelectDate(DateTime(datePickerState.selectedDateMillis))
                        onDismissRequest()
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                text = "OK",
                style = OTextStyle.Title2,
                color = ColorToken.TEXT_PRIMARY
            )
            Spacer(modifier = Modifier.width(8.dp))
        },
        dismissButton = {
            OText(
                modifier = Modifier
                    .noRippleClickable {
                        onDismissRequest()
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                ,
                text = "Cancel",
                style = OTextStyle.Title2,
                color = ColorToken.TEXT_02
            )
        },
        colors = DatePickerDefaults.colors().copy(
            containerColor = ColorToken.UI_BG.color()
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors().copy(
                containerColor = ColorToken.UI_BG.color(),
                titleContentColor = ColorToken.TEXT_01.color(),
                headlineContentColor = ColorToken.TEXT_01.color(),
                weekdayContentColor = ColorToken.TEXT_01.color(),
                subheadContentColor = ColorToken.TEXT_01.color(),
                navigationContentColor = ColorToken.TEXT_01.color(),
                dividerColor = ColorToken.STROKE_02.color(),

                yearContentColor = ColorToken.TEXT_01.color(),
                selectedYearContentColor = ColorToken.TEXT_ON_01.color(),
                currentYearContentColor = ColorToken.TEXT_01.color(),
                selectedYearContainerColor = ColorToken.UI_PRIMARY.color(),
                disabledYearContentColor = ColorToken.TEXT_DISABLE.color(),

                todayContentColor = ColorToken.TEXT_PRIMARY.color(),
                todayDateBorderColor = ColorToken.UI_BG.color(),
                selectedDayContentColor = ColorToken.TEXT_ON_01.color(),
                selectedDayContainerColor = ColorToken.UI_PRIMARY.color(),
                disabledDayContentColor = ColorToken.TEXT_DISABLE.color()
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ODatePickerDialogPreview() {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = DateTime.now().millis,
        selectableDates = TodayOrBeforeSelectableDates()
    )
    ODatePickerDialog(state, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
class TodayOrBeforeSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis.isFutureThanToday()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year.isFutureThanThisYear()
    }
}