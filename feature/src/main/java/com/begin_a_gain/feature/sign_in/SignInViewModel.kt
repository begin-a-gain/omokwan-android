package com.begin_a_gain.feature.sign_in

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(

) : ViewModel(), ContainerHost<SignInState, SignInSideEffect> {

    override val container: Container<SignInState, SignInSideEffect> = container(SignInState())

    fun failedToKakaoSignUp(throwable: Throwable) = intent {
        postSideEffect(SignInSideEffect.SignInFailed)
    }

    fun successToKakaoSignUp(token: OAuthToken) = intent {
        reduce { state.copy(accessToken = token.accessToken) }
        if (AuthApiClient.instance.hasToken()) {
            postSideEffect(SignInSideEffect.NavigateToMain)
        } else {
            postSideEffect(SignInSideEffect.NavigateToSignUp)
        }
    }
}