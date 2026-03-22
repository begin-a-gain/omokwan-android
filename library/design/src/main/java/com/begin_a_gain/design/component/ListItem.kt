package com.begin_a_gain.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color

@Composable
fun OListLazyColumn(
    modifier: Modifier,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorToken.UI_02.color()),
        contentPadding = PaddingValues(20.dp)
    ) {
        content()
    }
}

@Composable
fun Modifier.listItemBackground(isFirst: Boolean, isLast: Boolean) = Modifier
    .fillMaxWidth()
    .run {
        if (isFirst) {
            clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        } else if (isLast) {
            clip(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
        } else {
            this
        }
    }
    .background(ColorToken.UI_BG.color())