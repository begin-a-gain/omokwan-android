package com.begin_a_gain.feature.match.match

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.match.util.MatchCalendarRow
import org.joda.time.DateTime
import org.joda.time.Days
import java.util.Locale

private const val totalDays = 365*100

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MatchVerticalCalendar(
    modifier: Modifier = Modifier,
    startDate: DateTime = DateTime.now(),
    itemSize: Dp = 58.dp
) {
    val today = DateTime.now().withTimeAtStartOfDay()
    val start = startDate.withTimeAtStartOfDay()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val endDay = start.plusDays(totalDays)
        val daysDistance = Days.daysBetween(today, endDay).days
        val monthsDistance = (endDay.year - today.year) * 12 + (endDay.monthOfYear - today.monthOfYear)
        val indexOfToday = daysDistance + monthsDistance
        listState.scrollToItem(index = indexOfToday - 4)
    }

    val months = remember(startDate) {
        (0..totalDays).map { index ->
            start.plusDays(totalDays - index)
        }.groupBy { it.toString("yyyy. MM월") }.toList()
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        months.forEach { (monthTitle, daysInMonth) ->
            stickyHeader(key = monthTitle) {
                CalendarStickyHeader(header = monthTitle, isSticky = true)
            }

            items(
                items = daysInMonth,
                key = { it.toString() }
            ) { currentDate ->
                MatchCalendarRow(
                    today = currentDate.isEqual(today),
                    day = currentDate.dayOfWeek().getAsShortText(Locale.KOREAN).take(1),
                    date = currentDate.dayOfMonth,
                    size = itemSize
                )

                if (currentDate.dayOfMonth == 1) {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}