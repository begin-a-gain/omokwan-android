package com.begin_a_gain.feature.match.create_match

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType

data class CreateMatchState(
    override val loadingCount: Int = 0,
    val title: String = "",
    val selectedRepeatDayType: RepeatDayType = RepeatDayType.Weekday,
    val selectedDay: List<Boolean> = listOf(false, false, false, false, false, false, false),
    val maxParticipantsCount: Int = 5,
    val categoryList: List<MatchCategoryItem> = emptyList(),
    val selectedCategory: MatchCategoryItem? = null,
    val alarmOn: Boolean = false,
    val alarmHour: Int = 0,
    val alarmMin: Int = 0,
    val isPrivate: Boolean = false,
    val code: String = ""
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface CreateMatchSideEffect {
    data object CreateSuccess: CreateMatchSideEffect
}