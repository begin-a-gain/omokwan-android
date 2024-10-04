package com.begin_a_gain.feature.sign_in

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val context = LocalContext.current

    val kakaoSignInCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            viewModel.failedToKakaoSignUp(error)
        } else if (token != null) {
            viewModel.successToKakaoSignUp(token)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                signInWithKakao(context, kakaoSignInCallBack, viewModel::successToKakaoSignUp)
            }
        ) {
            Text(text = "KAKAO")
        }

        Text(text = state.accessToken)
    }
}

fun signInWithKakao(
    context: Context,
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