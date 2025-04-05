package com.begin_a_gain.feature.match.match.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.util.noRippleClickable
import com.google.android.material.color.MaterialColors

@Composable
fun MatchCalendarItem(
    isMine: Boolean,
    status: MatchCalendarStatus,
    modifier: Modifier = Modifier,
    size: Dp = 58.dp,
    onClick: () -> Unit = {},
) {
    Box(modifier = modifier) {
        OImage(
            modifier = Modifier
                .size(size)
                .noRippleClickable {
                    if (status == MatchCalendarStatus.Todo) {
                        onClick()
                    }
                },
            image = when (status) {
                MatchCalendarStatus.None -> if (isMine) OImageRes.CalendarMyNone else OImageRes.CalendarOthersNone
                MatchCalendarStatus.Combo -> if (isMine) OImageRes.CalendarMyCombo else OImageRes.CalendarOthersCombo
                MatchCalendarStatus.Done -> if (isMine) OImageRes.CalendarMyDone else OImageRes.CalendarOthersDone
                MatchCalendarStatus.Todo -> OImageRes.CalendarNew
            },
        )
    }
}

@Preview
@Composable
fun MatchCalendarItemPreview() {
    Row(
        modifier = Modifier.background(ColorToken.UI_BG.color())
    ) {
        Column {
            MatchCalendarStatus.entries.forEach {
                MatchCalendarItem(isMine = true, status = it)
            }
        }
        Column {
            MatchCalendarStatus.entries.forEach {
                MatchCalendarItem(isMine = false, status = it)
            }
        }
    }
}