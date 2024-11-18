package com.begin_a_gain.library.design.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color

@Composable
fun OIconButton(
    icon: OImageRes,
    backgroundColor: ColorToken,
    size: Dp,
    iconSize: Dp,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor.color())
            .size(size)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        OImage(
            image = icon,
            color = iconColor,
            size = iconSize
        )
    }
}