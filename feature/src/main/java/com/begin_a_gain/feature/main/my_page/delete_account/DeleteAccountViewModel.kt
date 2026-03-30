package com.begin_a_gain.feature.main.my_page.delete_account

import com.begin_a_gain.core.analytics.AnalyticsEvent
import com.begin_a_gain.core.analytics.AnalyticsHelper
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.request.DeletionSurveyRequest
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    analyticsHelper: AnalyticsHelper
): BaseViewModel<DeleteAccountState, DeleteAccountSideEffect>(DeleteAccountState(), analyticsHelper) {

    fun selectReason(reason: DeleteAccountReason) = intent {
        reduce {
            state.copy(
                reasons = if (reason in state.reasons) state.reasons - reason
                else state.reasons + reason
            )
        }
    }

    fun setOtherReason(text: String) = blockingIntent {
        reduce {
            state.copy(
                otherReason = text
            )
        }
    }

    fun deleteAccount() = intent {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "delete_account", screen = "delete_account"))
        withLoading {
            userRepository.postDeletionSurvey(
                request = DeletionSurveyRequest(
                    reasons = state.reasons.map { it.name },
                    otherReason = if (DeleteAccountReason.OTHER in state.reasons) state.otherReason else ""
                )
            ).onSuccess {
                userRepository.deleteAccount()
                    .onSuccess {
                        logEvent(AnalyticsEvent.DeleteAccount())
                        intent {
                            postSideEffect(DeleteAccountSideEffect.SuccessToDelete)
                        }
                    }
            }.onFailure {

            }
        }
    }
}
