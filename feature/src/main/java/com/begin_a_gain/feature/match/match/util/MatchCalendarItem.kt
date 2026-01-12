package com.begin_a_gain.feature.match.match.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.AppColors
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.advanceShadow
import com.begin_a_gain.design.util.noRippleClickable

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
                                if (isMine) ColorToken.UI_PRIMARY
                                    .color()
                                    .copy(alpha = 0.3f)
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
fun MatchCalendarRow(
    today: Boolean = true,
    day: String = "금",
    date: Int = 30,
    statusList: List<MatchCalendarStatus> = listOf(MatchCalendarStatus.None, MatchCalendarStatus.None),
    size: Dp = 58.dp
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .run {
                if (date == 1) {
                    clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                } else {
                    this
                }
            }
    ) {
        Box(
            modifier = Modifier
                .background(ColorToken.UI_02.color())
                .size(width = size + 6.dp, height = size)
                .padding(horizontal = 13.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .run {
                        if (today) {
                            background(
                                color = ColorToken.UI_BG.color(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        } else this
                    }
                    .matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (today) {
                    OText(text = day, style = OTextStyle.Subtitle3)
                    OText(text = "$date", style = OTextStyle.Headline)
                } else {
                    OText(text = "$date", style = OTextStyle.Subtitle3)
                }
            }
        }
        (0 .. 4).map { index ->
            MatchCalendarItem(
                isMine = index == 0 ,
                status = statusList.getOrNull(index) ?: MatchCalendarStatus.None,
                size = size
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