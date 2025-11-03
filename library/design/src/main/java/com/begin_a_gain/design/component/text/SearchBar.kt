package com.begin_a_gain.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    keyword: String = "",
    hint: String = "",
    onKeywordChanged: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = ColorToken.UI_03.color(), shape = RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OImage(image = OImageRes.Search)
        Spacer(modifier = Modifier.width(4.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = keyword,
                onValueChange = onKeywordChanged,
                singleLine = true
            )
            if (keyword.isEmpty()) {
                OText(
                    text = hint,
                    style = OTextStyle.Body1,
                    color = ColorToken.TEXT_02
                )
            }
        }
    }
}