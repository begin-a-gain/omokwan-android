package com.begin_a_gain.feature.match.match.setting

data class MatchSettingState(
    val title: String,
    val daysInProgress: Int,
    val selectedDay: List<Boolean>,
    val maxParticipantsCount: Int,
    val selectedCategoryIndex: Int,
    val alarmOn: Boolean,
    val alarmHour: Int,
)