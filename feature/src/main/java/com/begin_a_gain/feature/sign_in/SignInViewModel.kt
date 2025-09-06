package com.begin_a_gain.feature.sign_in

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.core.util.SocialSignInUtil
import com.begin_a_gain.domain.model.request.SignInRequest
import com.begin_a_gain.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val socialSignInUtil: SocialSignInUtil,
    private val authRepository: AuthRepository
) : BaseViewModel<SignInState, SignInSideEffect>() {

    override val container: Container<SignInState, SignInSideEffect> = container(SignInState())

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
                    postSideEffect(SignInSideEffect.NavigateToMain)
                } else {
                    postSideEffect(SignInSideEffect.NavigateToSignUp)
                }
            }
        }.onFailure {
            // Todo
        }
    }

    private fun failedToKakaoSignUp(throwable: Throwable) = intent {
        postSideEffect(SignInSideEffect.SignInFailed)
    }
}