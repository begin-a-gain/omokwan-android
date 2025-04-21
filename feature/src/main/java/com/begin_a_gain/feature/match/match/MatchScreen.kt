package com.begin_a_gain.feature.match.match

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.match.util.MatchCalendarRow
import com.begin_a_gain.library.design.component.image.OImageRes
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
        }
    ) {
        MatchCalendar(calendarItemSize)
    }
}

@Preview
@Composable
fun MatchCalendar(itemSize: Dp = 58.dp) {
    val startDate = DateTime.now().minusDays(5)
    LazyColumn {
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