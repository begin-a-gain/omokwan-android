package com.begin_a_gain.feature.main.notification

import com.begin_a_gain.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
) : BaseViewModel<NotificationState, Nothing>(NotificationState()) {

    fun initialize() {

    }
}
