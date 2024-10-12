package com.begin_a_gain.library.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.image.OImage
import com.begin_a_gain.library.design.image.OImageRes
import com.begin_a_gain.library.design.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle

enum class ButtonStyle {
    Solid,
    Stroke,
    None
}

enum class ButtonType {
    Primary,
    Alert,
    Disable
}

enum class ButtonSize {
    Large,
    Medium
}

@Composable
fun OButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.Primary,
    style: ButtonStyle = ButtonStyle.Solid,
    size: ButtonSize = ButtonSize.Large,
    leadingIcon: OImageRes? = null,
    trailingIcon: OImageRes? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .height(
                when (size) {
                    ButtonSize.Large -> 48.dp
                    ButtonSize.Medium -> 38.dp
                }
            )
            .then(
                when (style) {
                    ButtonStyle.Solid -> {
                        Modifier.background(
                            when (type) {
                                ButtonType.Primary -> ColorToken.UI_PRIMARY.color()
                                ButtonType.Alert -> ColorToken.UI_ALERT.color()
                                ButtonType.Disable -> ColorToken.UI_DISABLE_01.color()
                            }
                        )
                    }

                    ButtonStyle.Stroke -> {
                        Modifier.border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = when (type) {
                                ButtonType.Primary -> ColorToken.STROKE_PRIMARY.color()
                                ButtonType.Alert -> ColorToken.STROKE_ALERT.color()
                                ButtonType.Disable -> ColorToken.STROKE_DISABLE.color()
                            }
                        )
                    }

                    ButtonStyle.None -> Modifier
                }
            )
            .clickable(
                enabled = type != ButtonType.Disable,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let { icon ->
            OImage(
                image = icon,
                size = 20.dp,
                color = when (style) {
                    ButtonStyle.Solid -> {
                        when(type) {
                            ButtonType.Primary,
                            ButtonType.Alert -> ColorToken.ICON_ON_01
                            ButtonType.Disable -> ColorToken.ICON_ON_DISABLE
                        }
                    }
                    ButtonStyle.Stroke,
                    ButtonStyle.None -> {
                        when(type) {
                            ButtonType.Primary -> ColorToken.ICON_PRIMARY
                            ButtonType.Alert -> ColorToken.ICON_ALERT
                            ButtonType.Disable -> ColorToken.ICON_DISABLE
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        OText(
            text = text,
            style = when (size) {
                ButtonSize.Large -> OTextStyle.Title2
                ButtonSize.Medium -> OTextStyle.Subtitle2
            },
            color = when(style) {
                ButtonStyle.Solid -> {
                    when(type) {
                        ButtonType.Primary,
                        ButtonType.Alert -> ColorToken.TEXT_ON_01
                        ButtonType.Disable -> ColorToken.TEXT_ON_DISABLE
                    }
                }
                else -> when(type) {
                    ButtonType.Primary -> ColorToken.TEXT_PRIMARY
                    ButtonType.Alert -> ColorToken.TEXT_ALERT
                    ButtonType.Disable -> ColorToken.TEXT_DISABLE
                }
            }
        )
        Spacer(modifier = Modifier.width(12.dp))
        trailingIcon?.let { icon ->
            OImage(
                image = icon,
                size = 20.dp,
                color = when (style) {
                    ButtonStyle.Solid -> {
                        when(type) {
                            ButtonType.Primary,
                            ButtonType.Alert -> ColorToken.ICON_ON_01
                            ButtonType.Disable -> ColorToken.ICON_ON_DISABLE
                        }
                    }
                    ButtonStyle.Stroke,
                    ButtonStyle.None -> {
                        when(type) {
                            ButtonType.Primary -> ColorToken.ICON_PRIMARY
                            ButtonType.Alert -> ColorToken.ICON_ALERT
                            ButtonType.Disable -> ColorToken.ICON_DISABLE
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Primary - Large
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Primary,
            style = ButtonStyle.Solid,
            size = ButtonSize.Large,
            leadingIcon = OImageRes.PlaceHolder,
            trailingIcon = OImageRes.PlaceHolder
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Primary,
            style = ButtonStyle.Stroke,
            size = ButtonSize.Large,
            leadingIcon = OImageRes.PlaceHolder,
            trailingIcon = OImageRes.PlaceHolder
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Primary,
            style = ButtonStyle.None,
            size = ButtonSize.Large
        ) {}

        // Error - Large
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Alert,
            style = ButtonStyle.Solid,
            size = ButtonSize.Large
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Alert,
            style = ButtonStyle.Stroke,
            size = ButtonSize.Large
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Alert,
            style = ButtonStyle.None,
            size = ButtonSize.Large
        ) {}

        // Disable - Large
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Disable,
            style = ButtonStyle.Solid,
            size = ButtonSize.Large,
            leadingIcon = OImageRes.PlaceHolder,
            trailingIcon = OImageRes.PlaceHolder
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Disable,
            style = ButtonStyle.Stroke,
            size = ButtonSize.Large,
            leadingIcon = OImageRes.PlaceHolder,
            trailingIcon = OImageRes.PlaceHolder
        ) {}
        OButton(
            modifier = Modifier.fillMaxWidth(),
            text = "BUTTON",
            type = ButtonType.Disable,
            style = ButtonStyle.None,
            size = ButtonSize.Large
        ) {}
    }
}