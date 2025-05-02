package com.begin_a_gain.library.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OPreview

@Composable
fun InitialText(
    text: String,
    itemWidth: Dp,
    modifier: Modifier = Modifier,
    verticalSpace: Dp = 4.dp,
    initialTextStyle: OTextStyle = OTextStyle.Display,
    initialTextColor: ColorToken = ColorToken.TEXT_01,
    fullTextStyle: OTextStyle = OTextStyle.Subtitle1,
    fullTextColor: ColorToken = ColorToken.TEXT_01,
    fullTextModifier: Modifier = Modifier.width(itemWidth),
    backgroundColor: Color = ColorToken.UI_03.color(),
    showFullText: Boolean = true,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(isClickable) {
                    onClick()
                }
        ) {
            Spacer(
                modifier = Modifier
                    .size(itemWidth - 10.dp)
                    .clip(CircleShape)
                    .background(
                        color = backgroundColor
                    )
            )
            OText(
                text = text.first().toString(),
                textAlign = TextAlign.Center,
                style = initialTextStyle,
                color = initialTextColor
            )
        }
        if (showFullText) {
            Spacer(modifier = Modifier.height(verticalSpace))
            OText(
                modifier = fullTextModifier,
                text = text,
                style = fullTextStyle,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = fullTextColor
            )
        }
    }
}

@Preview
@Composable
fun InitialTextPreview() {
    OPreview {
        InitialText(text = "안녕하세요", itemWidth = 50.dp) {}
        InitialText(text = "안녕하세요", itemWidth = 50.dp, showFullText = false) {}
        InitialText(
            text = "안녕하세요안녕하세요안녕하세요",
            itemWidth = 50.dp,
            verticalSpace = 10.dp,
            fullTextModifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}