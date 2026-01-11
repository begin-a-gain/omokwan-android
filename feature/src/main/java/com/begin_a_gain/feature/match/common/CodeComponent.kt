package com.begin_a_gain.feature.match.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.dialog.ODialog
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.OTextField
import com.begin_a_gain.design.component.text.TextFieldStatus
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.OTextStyle

@Composable
fun CodeTextField(
    code: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    onCodeChanged: (String) -> Unit
) {
    OTextField(
        text = code,
        hint = "0",
        modifier = modifier.size(52.dp),
        textAlign = TextAlign.Center,
        cursorColor = Color.Transparent,
        status = if (isValid) TextFieldStatus.Default else TextFieldStatus.Error,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    ) {
        onCodeChanged(it)
    }
}

@Composable
fun MatchCodeDialog(
    code: String = "",
    isValid: Boolean = true,
    onConfirmClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }

    var code1 by rememberSaveable { mutableStateOf(if (code.isBlank()) "" else "${code[0]}") }
    var code2 by rememberSaveable { mutableStateOf(if (code.isBlank()) "" else "${code[1]}") }
    var code3 by rememberSaveable { mutableStateOf(if (code.isBlank()) "" else "${code[2]}") }
    var code4 by rememberSaveable { mutableStateOf(if (code.isBlank()) "" else "${code[3]}") }

    ODialog(
        title = "대국 코드 설정",
        buttonText = "확인",
        onButtonClick = {
            val fullCode = "$code1$code2$code3$code4"
            if (fullCode.length == 4) {
                onConfirmClick(fullCode)
                onDismissRequest()
            } else {
                onDismissRequest()
            }
        },
        additionalButtonText = "취소",
        onAdditionalButtonClick = onDismissRequest,
        content = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CodeTextField(
                        code = code1,
                        isValid = isValid,
                        modifier = Modifier
                            .focusRequester(focusRequester1)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    code1 = ""
                                    code2 = ""
                                    code3 = ""
                                    code4 = ""
                                }
                            }
                    ) { code ->
                        code1 = code.filter { it.isDigit() }.takeLast(1)
                        focusRequester2.requestFocus()
                    }
                    CodeTextField(
                        code = code2,
                        isValid = isValid,
                        modifier = Modifier
                            .focusRequester(focusRequester2)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    if (code2.isNotBlank()) {
                                        focusRequester1.requestFocus()
                                    }
                                }
                            }
                    ) { code ->
                        code2 = code.filter { it.isDigit() }.takeLast(1)
                        focusRequester3.requestFocus()
                    }
                    CodeTextField(
                        code = code3,
                        isValid = isValid,
                        modifier = Modifier
                            .focusRequester(focusRequester3)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    if (code3.isNotBlank()) {
                                        focusRequester1.requestFocus()
                                    }
                                }
                            }
                    ) { code ->
                        code3 = code.filter { it.isDigit() }.takeLast(1)
                        focusRequester4.requestFocus()
                    }

                    CodeTextField(
                        code = code4,
                        isValid = isValid,
                        modifier = Modifier
                            .focusRequester(focusRequester4)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    if (code4.isNotBlank()) {
                                        focusRequester1.requestFocus()
                                    }
                                }
                            }
                    ) { code ->
                        code4 = code.filter { it.isDigit() }.takeLast(1)
                        if (code4.length == 1) {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    }
                }
                if (!isValid) {
                    Spacer(Modifier.height(10.dp))
                    OText(
                        text = "유효하지 않은 코드입니다.",
                        style = OTextStyle.Caption,
                        color = ColorToken.TEXT_ALERT
                    )
                }
            }
        },
        onDismissRequest = onDismissRequest
    )
}