package com.begin_a_gain.feature.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.begin_a_gain.design.component.button.OTextButton
import com.begin_a_gain.design.component.dialog.ProgressBar
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.AppColors
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.initScreen
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignInScreen(
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    SignInContent(state) {
        viewModel.signInWithKakao()
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.SignInFailed -> {

            }

            SignInSideEffect.NavigateToSignUp -> {
                navigateToSignUp()
            }

            SignInSideEffect.NavigateToMain -> {
                navigateToMain()
            }
        }
    }
}

@Preview
@Composable
fun SignInContent(
    state: SignInState = SignInState(),
    signInWithKakao: () -> Unit = {}
) {
    Column(
        modifier = Modifier.initScreen(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OImage(
            image = OImageRes.LoginLogo,
            size = null,
            modifier = Modifier.width(185.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        OImage(
            image = OImageRes.OmogiLogin,
            size = null,
            modifier = Modifier.width(344.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OImage(
            image = OImageRes.KakaoLogin,
            size = 60.dp,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                signInWithKakao()
            }
        )

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
                // Todo
            }

            OTextButton("개인정보처리방침") {
                // Todo
            }
        }

        Spacer(modifier = Modifier.height(38.dp))

        if (state.isLoading) {
            ProgressBar()
        }
    }
}