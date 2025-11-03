package com.begin_a_gain.core.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SocialSignInUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun signInWithKakao(
        kakaoSignInCallBack: (OAuthToken?, Throwable?) -> Unit,
        onSuccess: (OAuthToken) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(
                        context = context,
                        callback = kakaoSignInCallBack
                    )
                } else if (token != null) {
                    onSuccess(token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoSignInCallBack)
        }
    }
}