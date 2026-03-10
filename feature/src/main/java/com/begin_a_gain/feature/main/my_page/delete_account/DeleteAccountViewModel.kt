package com.begin_a_gain.feature.main.my_page.delete_account

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel<DeleteAccountState, Nothing>(DeleteAccountState()) {

    fun selectReason(index: Int) = intent {
        reduce {
            state.copy(
                reasons = if (index in state.reasons) state.reasons - index
                else state.reasons + index
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

    fun deleteAccount() {

    }
}
