package com.begin_a_gain.domain.enum

enum class MatchStatus {
    None,
    Todo,
    Done,
    Skip
}

enum class MatchDayType(val code: Int) {
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7),
    Weekdays(8),
    Weekend(9),
    Everyday(10)
}