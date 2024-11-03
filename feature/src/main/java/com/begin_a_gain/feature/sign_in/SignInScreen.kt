package com.begin_a_gain.feature.sign_in

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.library.design.component.button.OTextButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.initScreen
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Preview
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val kakaoSignInCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            viewModel.failedToKakaoSignUp(error)
        } else if (token != null) {
            viewModel.successToKakaoSignUp(token)
        }
    }

    Column(
        modifier = Modifier.initScreen(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .background(AppColors.Kakao)
                .clickable {
                    signInWithKakao(context, kakaoSignInCallBack, viewModel::successToKakaoSignUp)
                }
        ) {

        }

        Spacer(modifier = Modifier.height(42.dp))
        OText(
            text = "회원가입을 진행할 경우, 아래의 정책에 대해 동의한 것으로 간주합니다.",
            textAlign = TextAlign.Center,
            style = OTextStyle.Caption,
            color = ColorToken.TEXT_02
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OTextButton("이용약관") {

            }

            OTextButton("개인정보처리방침") {

            }
        }

        Spacer(modifier = Modifier.height(38.dp))
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