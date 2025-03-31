package com.begin_a_gain.library.design.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OPreview

@Composable
fun ODialog(
    title: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    message: String? = null,
    content: @Composable (() -> Unit)? = null,
    additionalButtonText: String? = null,
    onAdditionalButtonClick: (() -> Unit)? = null,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(ColorToken.UI_BG.color())
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (message == null && content == null) {
                    OText(
                        text = title,
                        style = OTextStyle.Title2,
                        textAlign = TextAlign.Center
                    )
                } else {
                    OText(
                        text = title,
                        style = OTextStyle.Headline,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (message != null) {
                        OText(
                            text = message,
                            style = OTextStyle.Body2,
                            textAlign = TextAlign.Center
                        )
                    } else if (content != null) {
                        content()
                    }
                }
            }

            Row(
                modifier = Modifier.height(52.dp)
            ) {
                additionalButtonText?.let { leftButton ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .background(ColorToken.UI_DISABLE_01.color())
                            .clickable {
                                onDismissRequest()
                                onAdditionalButtonClick ?: {}
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        OText(
                            text = leftButton,
                            style = OTextStyle.Title2,
                            color = ColorToken.TEXT_ON_DISABLE
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(ColorToken.UI_PRIMARY.color())
                        .clickable {
                            onDismissRequest()
                            onButtonClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    OText(
                        text = buttonText,
                        style = OTextStyle.Title2,
                        color = ColorToken.TEXT_ON_01
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ODialogPreview() {
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }

    OPreview(
        verticalSpace = 10.dp
    ) {
        OButton(text = "Dialog 1") {
            showDialog1 = true
        }

        OButton(text = "Dialog 2") {
            showDialog2 = true
        }

        OButton(text = "Dialog 3") {
            showDialog3 = true
        }
    }


    if (showDialog1) {
        ODialog(
            title = "Title",
            message = LoremIpsum(8).values.first(),
            buttonText = "확인",
            onButtonClick = { showDialog1 = false },
            additionalButtonText = "취소",
            onAdditionalButtonClick = { showDialog1 = false },
            onDismissRequest = { showDialog1 = false }
        )
    }

    if (showDialog2) {
        ODialog(
            title = "Title",
            message = LoremIpsum(8).values.first(),
            buttonText = "확인",
            onButtonClick = { showDialog2 = false },
            onDismissRequest = { showDialog2 = false }
        )
    }

    if (showDialog3) {
        ODialog(
            title = LoremIpsum(8).values.first(),
            buttonText = "확인",
            onButtonClick = { showDialog3 = false },
            additionalButtonText = "취소",
            onAdditionalButtonClick = { showDialog3 = false },
            onDismissRequest = { showDialog3 = false }
        )
    }
}