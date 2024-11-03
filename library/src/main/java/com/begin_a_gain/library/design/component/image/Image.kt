package com.begin_a_gain.library.design.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.util.OPreview

@Composable
fun OImage(
    image: OImageRes,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: ColorToken? = null,
) {
    Image(
        modifier = modifier.size(size),
        painter = painterResource(id = image.res),
        contentDescription = "image",
        colorFilter = color?.let {
            ColorFilter.tint(color.color())
        }
    )
}

@Preview
@Composable
fun OImagePreview() {
    OPreview {
        OImage(image = OImageRes.Checked)
        OImage(image = OImageRes.Checked, color = ColorToken.ICON_ON_DISABLE)
    }
}