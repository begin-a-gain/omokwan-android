package com.begin_a_gain.feature.match.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle

val categories: List<Pair<String, String>> = listOf(
    Pair("\u200D\uD83D\uDCAA\uFE0F", "운동"), Pair("\uD83C\uDFC3\u200D", "건강"),
    Pair("\uD83D\uDCD5", "독서"), Pair("\uD83C\uDF08", "취미"), Pair("\uD83E\uDEE7", "생활"),
    Pair("\uD83D\uDCDD", "공부"), Pair("\uD83D\uDD01", "연습"), Pair("\uD83D\uDDDE", "시사/교양"),
    Pair("\uD83E\uDD57", "다이어트"), Pair("\uD83D\uDCDA", "자기계발"), Pair("\uD83C\uDFB5", "악기")
)

@Preview(showSystemUi = true)
@Composable
fun MatchCategoryGrid(
    selectedIndex: Int = -1,
    onSelect: (Int) -> Unit = {}
) {
    CustomHorizontalStaggeredGrid(
        verticalSpacing = 12.dp,
        horizontalSpacing = 12.dp
    ) {
        categories.forEachIndexed { index, (emoji, text) ->
            CategoryChip(
                emoji = emoji,
                text = text,
                isSelected = selectedIndex == index,
                onSelect = {
                    onSelect(index)
                }
            )
        }
    }
}

@Composable
fun CustomHorizontalStaggeredGrid(
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(content = content , modifier = modifier) { measurables, constraints ->
        val placeable = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeable.forEach {
                if (x + it.width > constraints.maxWidth) {
                    x = 0
                    y += it.height + verticalSpacing.roundToPx()
                }
                it.placeRelative(x, y)
                x += it.width + horizontalSpacing.roundToPx()
            }
        }
    }
}

@Composable
fun CategoryChip(
    emoji: String,
    text: String,
    isSelected: Boolean,
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
            .wrapContentWidth()
    ) {
        OText(text = emoji, style = OTextStyle.Subtitle2)
        Spacer(modifier = Modifier.width(4.dp))
        OText(
            text = text,
            style = OTextStyle.Subtitle2,
            color = if (isSelected) ColorToken.TEXT_PRIMARY
            else ColorToken.TEXT_01
        )
    }
}