package com.begin_a_gain.feature.match.match.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.begin_a_gain.feature.match.match.CalendarItem
import com.begin_a_gain.feature.match.match.CalendarStickyHeader
import org.joda.time.DateTime
import java.util.Locale

private const val totalDays = 365*100

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchVerticalCalendar(
    calendarItems: LazyPagingItems<CalendarItem>,
    modifier: Modifier = Modifier,
    itemSize: Dp = 58.dp
) {
    val today = DateTime.now().withTimeAtStartOfDay()
    val listState = rememberLazyListState()

    LaunchedEffect(calendarItems.itemCount) {
        if (calendarItems.itemCount > 0) {
            val todayIndex = (0 until calendarItems.itemCount).firstOrNull { i ->
                val item = calendarItems[i]
                item is CalendarItem.Day && DateTime.parse(item.data.date).isEqual(today)
            }

            todayIndex?.let {
                listState.scrollToItem(index = it, scrollOffset = -200)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = calendarItems.itemCount,
            key = calendarItems.itemKey { item ->
                when (item) {
                    is CalendarItem.Header -> "header_${item.title}"
                    is CalendarItem.Day -> "day_${item.data.date}"
                }
            }
        ) { index ->
            when (val item = calendarItems[index]) {
                is CalendarItem.Header -> {
                    CalendarStickyHeader(header = item.title, isSticky = true)
                }

                is CalendarItem.Day -> {
                    val currentDate = DateTime.parse(item.data.date)
                    MatchCalendarRow(
                        today = currentDate.isEqual(today),
                        day = currentDate.dayOfWeek().getAsShortText(Locale.KOREAN).take(1),
                        date = currentDate.dayOfMonth,
                        size = itemSize,
                        statusList = item.data.userStatus.map { it.status }
                    )

                    if (currentDate.dayOfMonth == 1) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                null -> {}
            }
        }
    }
}