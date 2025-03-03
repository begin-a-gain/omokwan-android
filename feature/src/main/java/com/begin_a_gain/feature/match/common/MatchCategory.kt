package com.begin_a_gain.feature.match.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.create_match.util.constant.categories
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.oDefaultPadding

@Preview(showSystemUi = true)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatchCategoryGrid(
    modifier: Modifier = Modifier,
    selectedIndex: Int = -1,
    onSelect: (Int) -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    sheetState: SheetState,
    selectedIndex: Int,
    onDismissRequest: () -> Unit,
    onSelected: (Int) -> Unit
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(selectedIndex) }

    OBottomSheet(
        title = "대국 카테고리",
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            MatchCategoryGrid(
                modifier = Modifier.oDefaultPadding(),
                selectedIndex = currentIndex
            ) {
                currentIndex = it
            }
            Spacer(modifier = Modifier.weight(1f))
            OButton(
                modifier = Modifier
                    .oDefaultPadding()
                    .fillMaxWidth(),
                text = "확인"
            ) {
                onSelected(currentIndex)
            }
        }
    }
}