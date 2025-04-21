package com.begin_a_gain.feature.match.match.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.domain.enum.MatchStatus
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.util.advanceShadow
import com.begin_a_gain.library.design.util.noRippleClickable

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
                .background(
                    if (isMine) ColorToken.UI_PRIMARY
                        .color()
                        .copy(0.1f) else Color.Transparent
                ),
            image = if (isMine) OImageRes.CalendarMyNone else OImageRes.CalendarOthersNone
        )

        if (status != MatchCalendarStatus.None) {
            OImage(
                modifier = Modifier
                    .size(size)
                    .padding(5.dp)
                    .advanceShadow(
                        color = when (status) {
                            MatchCalendarStatus.Combo -> {
                                if (isMine) ColorToken.UI_PRIMARY.color().copy(alpha = 0.3f)
                                else AppColors.OmokGrayShadow.copy(alpha = 0.4f)
                            }

                            MatchCalendarStatus.Done -> {
                                AppColors.OmokGrayShadow.copy(alpha = 0.4f)
                            }

                            else -> Color.Transparent
                        },
                        blurRadius = 8.dp,
                        borderRadius = 200.dp
                    )
                    .noRippleClickable {
                        if (status == MatchCalendarStatus.Todo) {
                            onClick()
                        }
                    },
                image = when (status) {
                    MatchCalendarStatus.Combo -> if (isMine) OImageRes.CalendarMyCombo else OImageRes.CalendarOthersCombo
                    MatchCalendarStatus.Done -> OImageRes.CalendarDone
                    else -> OImageRes.CalendarNew
                }
            )
        }
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