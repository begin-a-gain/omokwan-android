package com.begin_a_gain.library.design.component.selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.R
import com.begin_a_gain.library.design.util.OPreview
import com.begin_a_gain.library.design.util.noRippleClickable

@Composable
fun ORadioButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onChecked: () -> Unit
) {
    Image(
        modifier = modifier
            .size(24.dp)
            .noRippleClickable(enable) { onChecked() },
        painter = painterResource(
            id = when {
                checked && enable -> R.drawable.radio_checked_lt
                !checked && enable -> R.drawable.radio_unchecked_lt
                checked && !enable -> R.drawable.radio_disable_checked_lt
                else -> R.drawable.radio_disable_unchecked_lt
            }
        ),
        contentDescription = "radio"
    )
}

@Composable
@Preview
fun ORadioButtonPreview() {
    var checked by remember { mutableStateOf(false) }
    OPreview {
        ORadioButton(checked = checked) {
            checked = !checked
        }
        ORadioButton(checked = checked, enable = false) {
            checked = !checked
        }
        ORadioButton(checked = checked) {
            checked = !checked
        }
    }
}