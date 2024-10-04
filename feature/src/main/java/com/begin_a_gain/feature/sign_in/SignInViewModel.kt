package com.begin_a_gain.feature.sign_in

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.begin_a_gain.feature.test.TAG
import com.begin_a_gain.library.core.AppBuildConfig
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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