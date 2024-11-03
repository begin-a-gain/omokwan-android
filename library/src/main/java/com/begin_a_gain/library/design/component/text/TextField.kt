package com.begin_a_gain.library.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OPreview
import com.begin_a_gain.library.design.util.addFocusCleaner
import com.begin_a_gain.library.design.util.noRippleClickable

enum class TextFieldStatus {
    Default,
    Error,
    Disabled,
    ReadOnly
}

@Composable
fun OTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    message: String? = null,
    singleLine: Boolean = true,
    maxCount: Int = Int.MAX_VALUE,
    status: TextFieldStatus = TextFieldStatus.Default,
    keyboardOptions: KeyboardOptions? = null,
    onKeyboardDoneClick: () -> Unit = {},
    leadingIcon: OImageRes? = null,
    leadingIconColor: ColorToken? = null,
    trailingIcon: OImageRes? = null,
    trailingIconColor: ColorToken? = null,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        label?.let {
            OText(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = label,
                style = OTextStyle.Subtitle2
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .addFocusCleaner(focusManager)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = when (status) {
                        TextFieldStatus.Default -> {
                            if (isFocused) {
                                ColorToken.STROKE_FOCUS.color()
                            } else {
                                ColorToken.STROKE_02.color()
                            }
                        }

                        TextFieldStatus.Error -> ColorToken.STROKE_ALERT.color()
                        TextFieldStatus.Disabled,
                        TextFieldStatus.ReadOnly -> Color.Transparent
                    }
                )
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = when (status) {
                        TextFieldStatus.Default,
                        TextFieldStatus.Error -> ColorToken.UI_BG.color()

                        TextFieldStatus.Disabled,
                        TextFieldStatus.ReadOnly -> ColorToken.UI_DISABLE_01.color()
                    }
                )
                .padding(16.dp),
            value = text,
            onValueChange = onValueChange,
            enabled = when (status) {
                TextFieldStatus.Disabled,
                TextFieldStatus.ReadOnly -> false

                else -> true
            },
            readOnly = status == TextFieldStatus.ReadOnly,
            singleLine = singleLine,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onKeyboardDoneClick()
                }
            ),
            keyboardOptions = if (singleLine) {
                keyboardOptions ?: KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            } else {
                KeyboardOptions.Default.copy(imeAction = ImeAction.None)
            },
            cursorBrush = SolidColor(ColorToken.TEXT_01.color()),
            textStyle = TextStyle(
                fontSize = OTextStyle.Body2.typography.fontSize,
                fontWeight = OTextStyle.Body2.typography.fontWeight,
                color = when (status) {
                    TextFieldStatus.Disabled -> ColorToken.TEXT_ON_DISABLE.color()
                    else -> ColorToken.TEXT_01.color()
                },
                letterSpacing = OTextStyle.Body2.typography.letteringSpacing,
                lineHeight = OTextStyle.Body2.typography.lineHeight,
                textAlign = TextAlign.Start
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
                ) {
                    TextFieldLeadingIcon(
                        leadingIcon = leadingIcon,
                        color = leadingIconColor
                            ?: if (status == TextFieldStatus.Disabled) ColorToken.ICON_ON_DISABLE
                            else null
                    )

                    TextFieldWithHint(
                        text = text,
                        hint = hint,
                        focus = isFocused
                    ) {
                        innerTextField()
                    }

                    TextFieldTrailingIcon(
                        trailingIcon = trailingIcon,
                        color = trailingIconColor
                            ?: if (status == TextFieldStatus.Disabled) ColorToken.ICON_ON_DISABLE
                            else null,
                        onTrailingIconClick
                    )
                }
            }
        )

        if (maxCount != Int.MAX_VALUE || message != null) {
            TextFieldMessage(
                status = status,
                text = text,
                message = message,
                maxCount = maxCount
            )
        }
    }
}

@Composable
fun TextFieldLeadingIcon(
    leadingIcon: OImageRes?,
    color: ColorToken?
) {
    leadingIcon?.let {
        OImage(
            image = leadingIcon,
            size = 20.dp,
            color = color
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
private fun TextFieldWithHint(
    text: String,
    hint: String?,
    focus: Boolean,
    innerTextField: @Composable () -> Unit
) {
    if (text.isEmpty() && !focus) {
        OText(
            modifier = Modifier.fillMaxWidth(),
            text = hint ?: "",
            style = OTextStyle.Body2,
            color = ColorToken.TEXT_DISABLE,
            textAlign = TextAlign.Start,
        )
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = ColorToken.UI_PRIMARY.color(),
        backgroundColor = ColorToken.UI_PRIMARY.color()
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        innerTextField()
    }
}

@Composable
fun TextFieldTrailingIcon(
    trailingIcon: OImageRes?,
    color: ColorToken?,
    onTrailingIconClick: () -> Unit = {},
) {
    trailingIcon?.let { icon ->
        Spacer(modifier = Modifier.width(12.dp))
        OImage(
            modifier = Modifier.noRippleClickable { onTrailingIconClick() },
            image = icon,
            size = 20.dp,
            color = color
        )
    }
}

@Composable
fun TextFieldMessage(
    status: TextFieldStatus,
    text: String,
    message: String?,
    maxCount: Int
) {
    val messageColor = when (status) {
        TextFieldStatus.Error -> ColorToken.TEXT_ALERT
        else -> ColorToken.TEXT_02
    }

    Spacer(modifier = Modifier.height(6.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
        OText(
            modifier = Modifier.weight(1f),
            text = message ?: "",
            style = OTextStyle.Caption,
            color = messageColor
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (maxCount != Int.MAX_VALUE) {
            OText(
                text = "${text.count()}",
                style = OTextStyle.Caption,
                color = messageColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            OText(
                text = "/",
                style = OTextStyle.Caption,
                color = messageColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            OText(
                text = "$maxCount",
                style = OTextStyle.Caption,
                color = messageColor
            )
        }
    }
}

@Preview
@Composable
fun OTextFieldPreview() {
    val focusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf("")
    }

    OPreview(
        modifier = Modifier.addFocusCleaner(focusManager),
        verticalSpace = 10.dp
    ) {
        OTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "TextField (Default)",
            text = text,
            hint = "Fill this text field",
            maxCount = 10
        ) {
            text = it
        }

        OTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "TextField (Default) - leading icon",
            text = text,
            hint = "Fill this text field",
            leadingIcon = OImageRes.Checked
        ) {
            text = it
        }

        OTextField(
            label = "TextField (Error)",
            text = text,
            hint = "Fill this text field",
            status = TextFieldStatus.Error
        ) {
            text = it
        }

        OTextField(
            label = "TextField (Disabled)",
            text = text,
            hint = "Fill this text field",
            status = TextFieldStatus.Disabled
        ) {
            text = it
        }

        OTextField(
            label = "TextField (Disabled) - leading icon",
            text = text,
            hint = "Fill this text field",
            status = TextFieldStatus.Disabled,
            leadingIcon = OImageRes.Checked
        ) {
            text = it
        }

        OTextField(
            label = "TextField (ReadOnly)",
            text = text,
            hint = "Fill this text field",
            status = TextFieldStatus.ReadOnly
        ) {
            text = it
        }
    }
}