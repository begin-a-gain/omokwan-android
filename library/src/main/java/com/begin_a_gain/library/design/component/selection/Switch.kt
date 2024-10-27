package com.begin_a_gain.library.design.component.selection

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.util.OPreview

@Composable
fun OSwitch(
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Switch(
        modifier = Modifier.size(52.dp, 32.dp),
        checked = checked,
        onCheckedChange = {
            onCheckedChange()
        },
        thumbContent = {
            Spacer(modifier = Modifier.size(28.dp))
        },
        colors = SwitchDefaults.colors().copy(
            checkedThumbColor = ColorToken.UI_BG.color(),
            uncheckedThumbColor = ColorToken.UI_BG.color(),
            checkedTrackColor = ColorToken.UI_PRIMARY.color(),
            uncheckedTrackColor = ColorToken.UI_DISABLE_02.color(),
            checkedBorderColor = ColorToken.UI_BG.color(),
            uncheckedBorderColor = ColorToken.UI_BG.color()
        )
    )
}

@Composable
@Preview
fun OSwitchPreview() {
    var checked by remember { mutableStateOf(false) }
    OPreview {
        OSwitch(
            checked = checked,
            onCheckedChange = {
                checked = !checked
            }
        )
    }
}