package com.begin_a_gain.feature.sign_up

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.core.type.ValidationState
import com.begin_a_gain.domain.exception.SourceException
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalRepository
) : BaseViewModel<SignUpState, SignUpSideEffect>() {

    override val container: Container<SignUpState, SignUpSideEffect> = container(SignUpState())

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
                            reduce { state.copy(nicknameValidation = ValidationState.Success) }
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
        viewModelScope.withLoading {
            userRepository.postNickname(state.nickname)
                .onSuccess {
                    localRepository.saveIsSignUpCompleted(true)
                    postSideEffect(SignUpSideEffect.SignUpSuccess)
                }
                .onFailure {
                    // Todo
                }
        }
    }
}