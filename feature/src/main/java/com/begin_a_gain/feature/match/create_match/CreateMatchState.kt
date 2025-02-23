package com.begin_a_gain.feature.match.create_match

import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType

data class CreateMatchState(
    val title: String = "",
    val selectedRepeatDayType: RepeatDayType = RepeatDayType.Weekday,
    val selectedDay: List<Boolean> = listOf(false, false, false, false, false, false, false),
    val maxParticipantsCount: Int = 5,
    val selectedCategoryIndex: Int = -1,
    val alarmOn: Boolean = false,
    val isPrivate: Boolean = false
)

interface CreateMatchSideEffect {

}