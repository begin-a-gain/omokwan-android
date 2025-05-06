package com.begin_a_gain.feature.match.common.match_setting

import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType

data class MatchSettingUiState(
    val title: String = "",
    val setMatchTitle: (String) -> Unit = {},
    val selectedRepeatDayType: RepeatDayType = RepeatDayType.Weekday,
    val setRepeatDayType: (RepeatDayType) -> Unit = {},
    val setRepeatDay: (Int) -> Unit = {},
    val selectedDay: List<Boolean> = listOf(false, false, false, false, false, false, false),
    val maxParticipantsCount: Int = 5,
    val setMaximumParticipants: (Int) -> Unit = {},
    val selectedCategoryIndex: Int = -1,
    val setCategory: (Int) -> Unit = {},
    val alarmOn: Boolean = false,
    val setAlarmOn: (value: Boolean, hour: Int?, min: Int?) -> Unit = { _, _, _ -> },
    val alarmHour: Int = 0,
    val alarmMin: Int = 0,
    val isPrivate: Boolean = false,
    val setPrivate: (value: Boolean, code: String?) -> Unit = { _, _ -> },
    val code: String = ""
)