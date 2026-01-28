package com.begin_a_gain.feature.match.match.setting

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MatchCategoryItem

data class MatchSettingState(
    override val loadingCount: Int = 0,
    val isHost: Boolean = false,
    val daysInProgress: Int = 0,
    val matchCode: String = "",
    val selectedDay: List<Boolean> = listOf(),
    val currentSettings: MatchSettingsItem = MatchSettingsItem(),
    val initialSettings: MatchSettingsItem = MatchSettingsItem(),
    val hasChanges: Boolean = false
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

data class MatchSettingsItem(
    val title: String = "",
    val maxParticipantsCount: Int = 0,
    val selectedCategory: MatchCategoryItem? = null,
    val isPrivate: Boolean = false,
    val password: String = ""
)

interface MatchSettingSideEffect {
    object SuccessToLeaveMatch: MatchSettingSideEffect
}