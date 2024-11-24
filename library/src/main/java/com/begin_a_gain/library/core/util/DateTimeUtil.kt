package com.begin_a_gain.library.core.util

import org.joda.time.DateTime

object DateTimeUtil {
    private val today = DateTime.now().withTimeAtStartOfDay()

    fun DateTime.toString(format: DateTimeFormat): String = this.toString(format.format)

    fun Long.isFutureThanToday(): Boolean {
        return this < today.plusDays(1).millis
    }

    fun Int.isFutureThanThisYear(): Boolean {
        return this <= today.year
    }

    fun DateTime.isToday(): Boolean {
        return this.withTimeAtStartOfDay().millis == today.millis
    }
}

enum class DateTimeFormat(val format: String) {
    FullDate("yyyy년 MM월 dd일"),
}