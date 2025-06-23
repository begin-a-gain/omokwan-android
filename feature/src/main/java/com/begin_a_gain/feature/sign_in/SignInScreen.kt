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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.begin_a_gain.design.component.button.OTextButton
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
                    viewModel.signInWithKakao()
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