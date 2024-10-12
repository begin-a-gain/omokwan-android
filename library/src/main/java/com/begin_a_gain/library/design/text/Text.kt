package com.begin_a_gain.library.design.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.theme.suitFontFamily

@Composable
fun OText(
    text: String,
    style: OTextStyle,
    modifier: Modifier = Modifier,
    color: ColorToken = ColorToken.text_01,
) {
    var lineCount by rememberSaveable { mutableIntStateOf(1) }

    Text(
        modifier = modifier,
        text = text,
        color = color.color(),
        fontFamily = suitFontFamily,
        fontSize = style.typography.fontSize,
        fontWeight = style.typography.fontWeight,
        letterSpacing = style.typography.letteringSpacing,
        lineHeight = if (lineCount > 1 && style.typography.multiLineHeight != null) {
            style.typography.multiLineHeight
        } else {
            style.typography.lineHeight
        },
        onTextLayout = { textLayoutResult ->
            lineCount = textLayoutResult.lineCount
        }
    )
}

@Preview
@Composable
private fun OTextPreview() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Display)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Headline)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Title2)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Title1)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Subtitle3)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Subtitle2)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Subtitle1)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Body2)
        OText(text = LoremIpsum().values.first().take(100), style = OTextStyle.Body2)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Body1)
        OText(text = LoremIpsum().values.first().take(100), style = OTextStyle.Body1)
        OText(text = LoremIpsum().values.first().take(10), style = OTextStyle.Caption)
        OText(text = LoremIpsum().values.first().take(100), style = OTextStyle.Caption)
    }
}