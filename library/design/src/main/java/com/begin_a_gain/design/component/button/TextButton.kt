package com.begin_a_gain.design.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.noRippleClickable

@Composable
fun OTextButton(
    text: String,
    modifier: Modifier = Modifier,
    colorToken: ColorToken = ColorToken.TEXT_02,
    onClick: () -> Unit
) {
    OText(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        text = text,
        style = OTextStyle.Caption,
        color = colorToken,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}