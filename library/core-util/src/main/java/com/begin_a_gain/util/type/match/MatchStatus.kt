package com.begin_a_gain.model.type.match

import com.begin_a_gain.util.common.DateTimeUtil.toDateTime
import org.joda.time.DateTime
import org.joda.time.Days

enum class MatchStatus {
    None,
    Todo,
    Done,
    Skip;

    companion object {
        fun getMatchStatus(date: String, isCompleted: Boolean): MatchStatus {
            val today = DateTime.now().withTimeAtStartOfDay()
            val selectedDay = date.toDateTime().withTimeAtStartOfDay()
            val days = Days.daysBetween(today, selectedDay).days
            return when {
                isCompleted -> Done
                !isCompleted && days == 0 -> Todo
                else -> Skip
            }
        }
    }
}

enum class MatchJoinStatus(val value: String) {
    Joinable("JOINABLE"),
    NotJoinable("NOT_JOINABLE");

    companion object {
        fun String.isJoinable(): Boolean {
            return when {
                this == Joinable.value -> true
                this == NotJoinable.value -> false
                else -> false
            }
        }
    }
}