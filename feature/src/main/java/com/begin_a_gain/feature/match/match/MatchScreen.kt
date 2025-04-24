package com.begin_a_gain.feature.match.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.match.util.MatchCalendarRow
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import org.joda.time.DateTime

@Preview
@Composable
fun MatchScreen() {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val calendarItemSize = (deviceWidth - 40.dp - 6.dp).div(5)

    OScreen(
        title = "대국방 이름",
        showBackButton = true,
        trailingIcon = OImageRes.Menu,
        onTrailingIconClick = {
            // Todo : navigate to menu screen
        },
        bottomButtonText = "오목두기",
        bottomButtonType = ButtonType.Primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MatchCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                itemSize = calendarItemSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            MatchMembers()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun MatchCalendar(
    modifier: Modifier = Modifier,
    itemSize: Dp = 58.dp
) {
    val startDate = DateTime.now().minusDays(5)
    LazyColumn(
        modifier = modifier
    ) {
        items(30) {
            MatchCalendarRow(
                today = it == 4,
                day = startDate.plusDays(it).dayOfWeek().asText.take(1),
                date = startDate.plusDays(it).dayOfMonth,
                size = itemSize
            )
        }
    }
}

@Preview
@Composable
fun MatchMembers(
    members: List<String> = listOf("준영", "생갈치1호의행방불명", "쥬짱", "연날리기"),
    itemWidth: Dp = 58.dp
) {
    Row(
        modifier = Modifier
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.width(itemWidth + 6.dp))
        (0..4).forEach { index ->
            if (index <= members.size) {
                Column(
                    modifier = Modifier
                        .width(itemWidth)
                        .padding(horizontal = 5.dp, vertical = 8.dp)
                ) {
                    if (index == members.size) {
                        Box(
                            modifier = Modifier
                                .size(itemWidth - 10.dp)
                                .run {
                                    val stroke = Stroke(
                                        width = 6f,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                10f,
                                                10f
                                            ), 10f
                                        )
                                    )
                                    drawBehind {
                                        drawCircle(
                                            color = AppColors.Gray400,
                                            style = stroke
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            OImage(
                                image = OImageRes.Plus,
                                size = 24.dp,
                                color = ColorToken.ICON_DISABLE.color()
                            )
                        }
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Spacer(
                                modifier = Modifier
                                    .size(itemWidth - 10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = if (index == 0) ColorToken.UI_PRIMARY.color()
                                        else if (index == members.size) Color.Transparent
                                        else ColorToken.UI_03.color()
                                    )
                            )
                            OText(
                                text = members[index][0].toString(),
                                textAlign = TextAlign.Center,
                                style = OTextStyle.Display,
                                color = if (index == 0) ColorToken.TEXT_ON_01 else ColorToken.TEXT_01
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    OText(
                        modifier = Modifier.width(itemWidth),
                        text = if (index == members.size) "멤버 추가" else members[index],
                        style = OTextStyle.Subtitle1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = if (index == 0) ColorToken.TEXT_PRIMARY
                        else if (index == members.size) ColorToken.TEXT_DISABLE
                        else ColorToken.TEXT_01
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(itemWidth))
            }
        }
    }
}