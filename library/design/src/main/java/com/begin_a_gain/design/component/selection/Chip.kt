package com.begin_a_gain.design.component.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle

@Composable
fun OChip(
    text: String,
    isSelected: Boolean,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable { onSelect() }
            .border(
                width = 1.dp,
                color = if (isSelected) ColorToken.STROKE_PRIMARY.color()
                else ColorToken.STROKE_01.color(),
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if (isSelected) ColorToken.UI_PRIMARY
                    .color()
                    .copy(alpha = .1f)
                else ColorToken.UI_BG.color()
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .wrapContentHeight()
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        leadingContent?.let {
            leadingContent()
        }
        OText(
            text = text,
            style = OTextStyle.Subtitle2,
            color = if (isSelected) ColorToken.TEXT_PRIMARY
            else ColorToken.TEXT_01
        )
        trailingContent?.let {
            trailingContent()
        }
    }
}