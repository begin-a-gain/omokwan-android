package com.begin_a_gain.design.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.util.OPreview

@Composable
fun OImage(
    image: OImageRes,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    color: Color? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        modifier = modifier.then(
            if (size != null) {
                Modifier.size(size)
            } else {
                Modifier
            }
        ),
        painter = painterResource(id = image.res),
        contentDescription = "image",
        colorFilter = color?.let {
            ColorFilter.tint(color)
        },
        contentScale = contentScale
    )
}

@Preview
@Composable
fun OImagePreview() {
    OPreview {
        OImage(image = OImageRes.Checked)
        OImage(image = OImageRes.Checked, color = ColorToken.ICON_ON_DISABLE.color())
    }
}