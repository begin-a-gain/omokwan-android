package com.begin_a_gain.feature.sign_in

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.core.util.SocialSignInUtil
import com.begin_a_gain.domain.model.request.SignInRequest
import com.begin_a_gain.domain.repository.AuthRepository
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val socialSignInUtil: SocialSignInUtil,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel<SignInState, SignInSideEffect>(SignInState()) {

    fun signInWithKakao() {
        socialSignInUtil.signInWithKakao(
            kakaoSignInCallBack = { token, error ->
                if (error != null) {
                    failedToKakaoSignUp(error)
                } else if (token != null) {
                    viewModelScope.withLoading {
                        signIn(token.accessToken)
                    }
                }
            },
            onSuccess = { token ->
                viewModelScope.withLoading {
                    signIn(token.accessToken)
                }
            }
        )
    }

    private suspend fun signIn(kakaoToken: String) {
        authRepository.kakaoSignIn(
            signInRequest = SignInRequest(kakaoToken)
        ).onSuccess { signUpComplete ->
            intent {
                if (signUpComplete) {
                    getUserInfo {
                        intent {
                            postSideEffect(SignInSideEffect.NavigateToMain)
                        }
                    }
                } else {
                    postSideEffect(SignInSideEffect.NavigateToSignUp)
                }
            }
        }.onFailure {
            // Todo
        }
    }

    private suspend fun getUserInfo(onSuccess: () -> Unit) {
        userRepository.getUserInfo()
            .onSuccess { onSuccess() }
    }

    private fun failedToKakaoSignUp(throwable: Throwable) = intent {
        postSideEffect(SignInSideEffect.SignInFailed)
    }
}