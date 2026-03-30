package com.begin_a_gain.feature.sign_up

import com.begin_a_gain.core.analytics.AnalyticsEvent
import com.begin_a_gain.core.analytics.AnalyticsHelper
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.exception.SourceException
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.UserRepository
import com.begin_a_gain.model.type.common.ValidationState
import com.begin_a_gain.util.enum.NicknameFailCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalRepository,
    analyticsHelper: AnalyticsHelper
) : BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState(), analyticsHelper) {

    init {
        validateNickname()
    }

    fun setNickname(nickname: String) = blockingIntent {
        reduce { state.copy(nickname = nickname) }
    }

    @OptIn(FlowPreview::class)
    private fun validateNickname() = intent {
        container.stateFlow
            .map { it.nickname }
            .distinctUntilChanged()
            .debounce(300)
            .filter { it.isNotBlank() }
            .collect { nickname ->
                if (nickname.length in 2..10) {
                    userRepository.postNicknameValidation(nickname)
                        .onSuccess {
                            if (it.isValid) {
                                reduce { state.copy(nicknameValidation = ValidationState.Success) }
                            } else {
                                reduce {
                                    state.copy(
                                        nicknameValidation = ValidationState.Fail,
                                        nicknameFailCase = if (it.isDuplicated) NicknameFailCase.Duplicated else NicknameFailCase.Unconventional
                                    )
                                }
                            }
                        }
                        .onFailure {
                            reduce {
                                state.copy(
                                    nicknameValidation = ValidationState.Fail,
                                    nicknameFailCase = when (it) {
                                        is SourceException.InvalidRequest -> {
                                            if (it.error.message.contains("already")) {
                                                NicknameFailCase.Duplicated
                                            } else NicknameFailCase.Unconventional
                                        }

                                        else -> NicknameFailCase.Unconventional
                                    }
                                )
                            }
                        }
                } else {
                    reduce {
                        state.copy(
                            nicknameValidation = ValidationState.Fail,
                            nicknameFailCase = NicknameFailCase.Unconventional
                        )
                    }
                }
            }
    }

    fun saveNickname() = intent {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "save_nickname", screen = "sign_up"))
        withLoading {
            userRepository.postNickname(state.nickname)
                .onSuccess {
                    localRepository.saveIsSignUpCompleted(true)
                    getUserInfo()
                }
                .onFailure {
                    // Todo
                }
        }
    }

    private fun getUserInfo() {
        withLoading {
            userRepository.getUserInfo()
                .onSuccess {
                    logEvent(AnalyticsEvent.SignUp(method = "kakao"))
                    intent {
                        postSideEffect(SignUpSideEffect.SignUpSuccess)
                    }
                }
                .onFailure {
                    intent {
                        postSideEffect(SignUpSideEffect.NavigateToSignIn)
                    }
                }
        }
    }
}