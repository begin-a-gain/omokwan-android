package com.begin_a_gain.library.design.util

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(
    enable: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        if (enable) {
            onClick()
        }
    }
}

fun Modifier.advanceShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 16.dp,
    blurRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spreadWidth: Dp = 0.dp,
    spreadHeight: Dp = 0.dp,
    spread: Float = 1f,
) = drawBehind {
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.dp.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx() - spreadWidth.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx() - spreadHeight.toPx()
        val rightPixel = (this.size.width) + spreadWidth.toPx()
        val bottomPixel = (this.size.height + spreadPixel) + offsetY.toPx() + spreadHeight.toPx()

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}

fun Modifier.fadingEdge(
    brush: Brush = Brush.verticalGradient(
        0f to Color.Transparent,
        0.5f to Color.Black,
        1f to Color.Transparent
    )
) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }