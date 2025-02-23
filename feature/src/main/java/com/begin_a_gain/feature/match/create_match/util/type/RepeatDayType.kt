package com.begin_a_gain.feature.match.create_match.util.type

enum class RepeatDayType(
    val title: String
) {
    Weekday("주중"),
    Weekend("주말"),
    Everyday("매일"),
    SpecificDay("직접선택")
}