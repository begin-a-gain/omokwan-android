package com.begin_a_gain.feature.main.notification

import com.begin_a_gain.core.analytics.AnalyticsEvent
import com.begin_a_gain.core.analytics.AnalyticsHelper
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.Notification
import com.begin_a_gain.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    analyticsHelper: AnalyticsHelper
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState(), analyticsHelper) {

    fun initialize() {
        withLoading {
            notificationRepository.getNotifications()
                .onSuccess {
                    intent {
                        reduce {
                            state.copy(notifications = it)
                        }
                    }
                }
        }
    }

    fun setFilter(filter: NotificationFilter) = intent {
        reduce {
            state.copy(filter = filter)
        }
    }

    fun readNotification(notification: Notification) = withLoading {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "read_notification", screen = "notification"))
        notificationRepository.patchRead(notification.notificationId)
            .onSuccess {
                initialize()
                intent {
                    postSideEffect(NotificationSideEffect.NavigateToMatch(notification.matchId))
                }
            }
    }

    fun readAllNotifications() = withLoading {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "read_all_notifications", screen = "notification"))
        notificationRepository.patchRead(null)
            .onSuccess {
                initialize()
            }
    }
}
