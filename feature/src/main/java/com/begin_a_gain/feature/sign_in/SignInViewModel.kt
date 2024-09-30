package com.begin_a_gain.feature.sign_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
    application: Application,
    private val buildConfig: AppBuildConfig
) : AndroidViewModel(application), ContainerHost<SignInState, SignInSideEffect> {

    override val container: Container<SignInState, SignInSideEffect> = container(SignInState())

    fun initKakaoSdk() {
        val context = getApplication<Application>().applicationContext
        KakaoSdk.init(context, buildConfig.getKakaoApiKey())
    }

    private val kakaoSignInCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            failedToKakaoSignUp(error)
        } else if (token != null) {
            successToKakaoSignUp(token)
        }
    }

    fun signInWithKakao() {
        val context = getApplication<Application>().applicationContext
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    failedToKakaoSignUp(error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(
                        context = context,
                        callback = kakaoSignInCallBack
                    )
                } else if (token != null) {
                    successToKakaoSignUp(token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoSignInCallBack)
        }
    }

    private fun failedToKakaoSignUp(throwable: Throwable) = intent {
        postSideEffect(SignInSideEffect.SignInFailed)
    }

    private fun successToKakaoSignUp(token: OAuthToken) = intent {
        if (AuthApiClient.instance.hasToken()) {
            postSideEffect(SignInSideEffect.NavigateToMain)
        } else {
            postSideEffect(SignInSideEffect.NavigateToSignUp)
        }
    }
}