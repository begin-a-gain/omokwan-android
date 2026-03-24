package com.begin_a_gain.feature.main.notification

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.Notification

data class NotificationState(
    override val loadingCount: Int = 0,
    val filter: NotificationFilter = NotificationFilter.All,
    val notifications: List<Notification> = emptyList()
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface NotificationSideEffect {
    data class NavigateToMatch(val matchId: Int): NotificationSideEffect
}

enum class NotificationFilter {
    All, Unread
}