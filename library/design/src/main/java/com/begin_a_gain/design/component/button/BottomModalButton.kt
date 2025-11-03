package com.begin_a_gain.design.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.util.advanceShadow

@Composable
fun BottomModalButton(
    buttonText: String,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .advanceShadow(
                color = ColorToken.UI_PRIMARY
                    .color()
                    .copy(0.3f),
                blurRadius = 20.dp,
            )
            .background(
                color = ColorToken.UI_BG.color(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(16.dp)

    ) {
        OButton(
            modifier = Modifier.fillMaxWidth(),
            type = if (enable) ButtonType.Primary else ButtonType.Disable,
            text = buttonText
        ) {
            onClick()
        }
    }
}