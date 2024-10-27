package com.begin_a_gain.library.design.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.noRippleClickable

@Composable
fun OTopBar(
    title: String,
    startIcon: OImageRes? = null,
    endIcon: OImageRes? = null,
    endText: String? = null,
    onClickStart: () -> Unit = {},
    onClickEnd: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorToken.UI_BG.color())
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        startIcon?.let {
            OImage(
                modifier = Modifier
                    .noRippleClickable { onClickStart() }
                    .align(Alignment.CenterStart),
                image = it,
                size = 24.dp,
            )
        }

        OText(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = OTextStyle.Headline
        )

        endText?.let {
            OText(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .noRippleClickable { onClickEnd() },
                text = it,
                style = OTextStyle.Body2
            )
        } ?: endIcon?.let {
            OImage(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .noRippleClickable { onClickEnd() },
                image = it,
                size = 24.dp
            )
        }
    }
}

@Composable
fun OLogoBar() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(ColorToken.UI_BG.color())
            .padding(horizontal = 20.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(160.dp, 40.dp))
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
@Preview
fun OTopBarPreview() {
    Column {
        OTopBar(
            title = "TITLE",
            startIcon = OImageRes.ArrowLeft,
            endText = "TEXT"
        )
        OTopBar(
            title = "TITLE",
            startIcon = OImageRes.ArrowLeft,
            endIcon = OImageRes.Bell
        )
    }
}