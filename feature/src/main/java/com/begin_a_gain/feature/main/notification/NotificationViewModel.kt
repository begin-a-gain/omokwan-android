package com.begin_a_gain.feature.main.notification

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.Notification
import com.begin_a_gain.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState()) {

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
        notificationRepository.patchRead(notification.notificationId)
            .onSuccess {
                initialize()
                intent {
                    postSideEffect(NotificationSideEffect.NavigateToMatch(notification.matchId))
                }
            }
    }

    fun readAllNotifications() = withLoading {
        notificationRepository.patchRead(null)
            .onSuccess {
                initialize()
            }
    }
}
