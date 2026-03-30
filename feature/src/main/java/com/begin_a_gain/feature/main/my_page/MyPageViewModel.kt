package com.begin_a_gain.feature.main.my_page

import com.begin_a_gain.core.analytics.AnalyticsEvent
import com.begin_a_gain.core.analytics.AnalyticsHelper
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.AuthRepository
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    analyticsHelper: AnalyticsHelper
): BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState(), analyticsHelper) {

    fun initiate() {
        withLoading {
            userRepository.getUserMyPage()
                .onSuccess {
                    intent {
                        reduce {
                            state.copy(
                                nickname = it.nickname,
                                inProgressMatches = it.inProgressMatchList,
                                completedMatches = it.completedMatchList
                            )
                        }
                    }
                }
        }
    }

    fun logout() = intent {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "logout", screen = "my_page"))
        logEvent(AnalyticsEvent.Logout())
        authRepository.logout()
        postSideEffect(MyPageSideEffect.LoggedOut)
    }
}
