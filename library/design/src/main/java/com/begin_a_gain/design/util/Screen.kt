package com.begin_a_gain.design.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.button.BottomModalButton
import com.begin_a_gain.design.component.button.ButtonStyle
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.navigation.OTopBar
import com.begin_a_gain.design.component.notification.OSnackBar
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import kotlinx.coroutines.launch

val ScreenHorizontalPadding = 20.dp

enum class ScreenBottomButtonType {
    Modal, Basic
}

@Composable
fun Modifier.initScreen(usePadding: Boolean = true) = this
    .fillMaxSize()
    .background(ColorToken.UI_BG.color())
    .padding(horizontal = if (usePadding) ScreenHorizontalPadding else 0.dp)

@Composable
fun OScreen(
    modifier: Modifier = Modifier,
    title: String? = null,
    trailingIcon: OImageRes? = null,
    onTrailingIconClick: () -> Unit = {},
    useDefaultPadding: Boolean = true,
    showBackButton: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    bottomButtonUiType: ScreenBottomButtonType = ScreenBottomButtonType.Basic,
    bottomButtonText: String? = null,
    bottomButtonStyle: ButtonStyle = ButtonStyle.Solid,
    bottomButtonType: ButtonType = ButtonType.Disable,
    snackBarBottomPadding: Dp = 0.dp,
    onBottomButtonClick: () -> Unit = {},
    content: @Composable (showSnackBar: (String) -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OTopBar(
                title = title ?: "",
                startIcon = if (showBackButton) OImageRes.ArrowLeft else null,
                onClickStart = onBackButtonClick,
                endIcon = trailingIcon,
                onClickEnd = onTrailingIconClick
            )
        },
        bottomBar = {
            bottomButtonText?.let {
                if (bottomButtonUiType == ScreenBottomButtonType.Basic) {
                    OButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 20.dp),
                        text = bottomButtonText,
                        style = bottomButtonStyle,
                        type = bottomButtonType
                    ) {
                        onBottomButtonClick()
                    }
                } else {
                    BottomModalButton(
                        buttonText = it,
                        enable = if (bottomButtonType == ButtonType.Disable) false else true,
                    ) {
                        onBottomButtonClick()
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(bottom = snackBarBottomPadding)
            ) { snackBarData ->
                OSnackBar(message = snackBarData.visuals.message)
            }
        }
    ) {
        Column(
            modifier
                .padding(it)
                .initScreen(useDefaultPadding)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                content { snackBarMessage ->
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = snackBarMessage,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }
}