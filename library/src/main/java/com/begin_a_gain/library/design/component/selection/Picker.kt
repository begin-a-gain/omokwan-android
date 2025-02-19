package com.begin_a_gain.library.design.component.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.OTextStyle

val pickerItemHeight = 40.dp

@Composable
fun OPicker(
    options: List<String>,
    modifier: Modifier = Modifier,
    onSelect: (Int) -> Unit
) {
    val pickerState = rememberLazyListState()
    val selectedIndex by remember {
        derivedStateOf { pickerState.firstVisibleItemIndex }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(pickerItemHeight)
                .drawBehind {
                    drawLine(
                        color = AppColors.Gray300,
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f)
                    )
                    drawLine(
                        color = AppColors.Gray300,
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height)
                    )
                }
        )

        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp, pickerItemHeight * 2),
            flingBehavior = rememberSnapFlingBehavior(pickerState)
        ) {
            items(options) { option ->
                Box(
                    modifier = Modifier
                        .height(pickerItemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OText(text = option, style = OTextStyle.Subtitle3)
                }
            }
        }
    }
}

@Preview
@Composable
fun OPickerPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .background(Color.White)
        ) {
            OPicker(
                options = (1..10).toList().map { "$it" },
                onSelect = {}
            )
        }
    }

}