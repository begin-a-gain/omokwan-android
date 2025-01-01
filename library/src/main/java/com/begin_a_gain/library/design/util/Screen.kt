package com.begin_a_gain.library.design.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.button.ButtonStyle
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.navigation.OTopBar
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color

val ScreenHorizontalPadding = 20.dp

@Composable
fun Modifier.initScreen(usePadding: Boolean = true) = this
    .fillMaxSize()
    .background(ColorToken.UI_BG.color())
    .padding(horizontal = if (usePadding) ScreenHorizontalPadding else 0.dp)

@Composable
fun OScreen(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBackButton: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    bottomButtonText: String? = null,
    bottomButtonStyle: ButtonStyle = ButtonStyle.Solid,
    bottomButtonType: ButtonType = ButtonType.Disable,
    onBottomButtonClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OTopBar(
            title = title?: "",
            startIcon = if (showBackButton) OImageRes.ArrowLeft else null,
            onClickStart = onBackButtonClick
        )
        Column(
            modifier.initScreen()
        ){
            Column(
                modifier = Modifier.weight(1f)
            ){
                content()
            }
            bottomButtonText?.let{
                OButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    text = bottomButtonText,
                    style = bottomButtonStyle,
                    type = bottomButtonType
                ) {
                    onBottomButtonClick()
                }
            }
        }
    }
}