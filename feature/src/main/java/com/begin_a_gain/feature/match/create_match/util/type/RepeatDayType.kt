package com.begin_a_gain.feature.match.create_match.util.type

import com.begin_a_gain.domain.enum.MatchDayType

enum class RepeatDayType(
    val title: String
) {
    Weekday("주중"),
    Weekend("주말"),
    Everyday("매일"),
    SpecificDay("직접선택");

    companion object {
        fun RepeatDayType.parseToMatchDayType(
            repeatDayList: List<Boolean>
        ): List<Int> {
            return when(this) {
                Weekday -> listOf(MatchDayType.Weekdays.code)
                Weekend -> listOf(MatchDayType.Weekend.code)
                Everyday -> listOf(MatchDayType.Everyday.code)
                SpecificDay -> {
                    repeatDayList.mapIndexedNotNull { index, daySelected ->
                        if (daySelected) {
                            val dayType = MatchDayType.entries[(index + 6) % 7]
                            dayType.code
                        } else null
                    }
                }
            }
        }
    }
}