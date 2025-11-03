package com.begin_a_gain.design.component.selection

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
import com.begin_a_gain.design.R
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.util.OPreview
import com.begin_a_gain.design.util.noRippleClickable

@Composable
fun OCheckBox(
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
                checked && enable -> R.drawable.checkbox_checked_lt
                !checked && enable -> R.drawable.checkbox_unchecked_lt
                checked && !enable -> R.drawable.checkbox_disable_checked_lt
                else -> R.drawable.checkbox_disable_unchecked_lt
            }
        ),
        contentDescription = "checkbox"
    )
}

@Composable
fun OCheck(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onChecked: () -> Unit
) {
    OImage(
        modifier = modifier.noRippleClickable { onChecked() },
        image = if (checked) OImageRes.Checked else OImageRes.Unchecked,
        size = 24.dp
    )
}

@Composable
@Preview
fun OCheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }
    OPreview {
        OCheckBox(checked = checked) {
            checked = !checked
        }
        OCheckBox(checked = checked, enable = false) {
            checked = !checked
        }
        OCheck(checked = checked) {
            checked = !checked
        }
    }
}