package com.begin_a_gain.model.type.match

import com.begin_a_gain.util.common.DateTimeUtil.toDateTime
import org.joda.time.DateTime

enum class MatchStatus {
    None,
    Todo,
    Done,
    Skip;

    companion object {
        fun getMatchStatus(date: String, isCompleted: Boolean): MatchStatus {
            val today = DateTime.now().withTimeAtStartOfDay()
            val selectedDay = date.toDateTime()
            return None
        }
    }
}