package com.begin_a_gain.library.core.util

import org.joda.time.DateTime

object DateTimeUtil {
    fun DateTime.toString(format: DateTimeFormat): String = this.toString(format.format)
}

enum class DateTimeFormat(val format: String) {
    FullDate("yyyy년 MM월 dd일"),
}