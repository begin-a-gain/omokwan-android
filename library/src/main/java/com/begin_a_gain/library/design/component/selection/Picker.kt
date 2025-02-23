package com.begin_a_gain.library.design.component.selection

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.fadingEdge

val pickerItemHeight = 40.dp

@Composable
fun OPicker(
    items: List<String>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
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
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fadingEdge(),
                contentPadding = PaddingValues(0.dp, pickerItemHeight * 2),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
            ) {
                items(items) { item ->
                    Box(
                        modifier = Modifier
                            .height(pickerItemHeight)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OText(text = item, style = OTextStyle.Body2)
                    }
                }
            }
        }
    }
}