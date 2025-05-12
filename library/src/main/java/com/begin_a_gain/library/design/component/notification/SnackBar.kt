package com.begin_a_gain.library.design.component.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OPreview
import com.begin_a_gain.library.design.util.noRippleClickable

@Composable
fun OSnackBar(
    message: String,
    button: String? = null,
    onButtonClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = ColorToken.UI_BG2.color(),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OText(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            text = message,
            style = OTextStyle.Body2,
            color = ColorToken.TEXT_ON_01
        )
        button?.let {
            Spacer(modifier = Modifier.width(12.dp))
            OText(
                modifier = Modifier
                    .width(75.dp)
                    .noRippleClickable {
                        onButtonClick()
                    },
                text = it,
                textAlign = TextAlign.Center,
                style = OTextStyle.Subtitle2,
                color = ColorToken.TEXT_PRIMARY
            )
        }
    }
}

@Composable
@Preview
fun OSnackBarPreview() {
    OPreview {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OSnackBar(LoremIpsum(5).values.first())
            OSnackBar(LoremIpsum(8).values.first())

            OSnackBar(
                LoremIpsum(5).values.first(),
                button = "Action"
            )
            OSnackBar(
                LoremIpsum(8).values.first(),
                button = "확인"
            )
        }
    }
}