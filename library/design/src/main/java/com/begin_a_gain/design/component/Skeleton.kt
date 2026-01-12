package com.begin_a_gain.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.util.shimmerEffect

@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    background: ColorToken = ColorToken.UI_BG,
    component: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        component()
        if (isLoading) {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = background.color(),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }

    }
}