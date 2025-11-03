package com.begin_a_gain.util.common

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DateTimeUtil {
    private val today = DateTime.now().withTimeAtStartOfDay()

    fun DateTime.toString(format: ODateTimeFormat): String = this.toString(format.format)

    fun Long.isFutureThanToday(): Boolean {
        return this < today.plusDays(1).millis
    }

    fun Int.isFutureThanThisYear(): Boolean {
        return this <= today.year
    }

    fun DateTime.isToday(): Boolean {
        return this.withTimeAtStartOfDay().millis == today.millis
    }

    fun String.toDateTime(): DateTime {
        val formatter = DateTimeFormat.forPattern(ODateTimeFormat.DateForNetwork.format)
        val dateTime = DateTime.parse(this, formatter).withTimeAtStartOfDay()
        return dateTime
    }
}

enum class ODateTimeFormat(val format: String) {
    FullDate("yyyy년 MM월 dd일"),
    DateForNetwork("yyyy-MM-dd")
}