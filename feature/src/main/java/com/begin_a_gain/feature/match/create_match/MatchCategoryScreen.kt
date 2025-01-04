package com.begin_a_gain.feature.match.create_match

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.feature.match.common.MatchCategoryGrid
import com.begin_a_gain.library.design.component.button.OTextButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen

@Preview
@Composable
fun MatchCategoryScreen() {
    OScreen(
        bottomButtonText = "다음",
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            OText(text = "어떤 대국인가요?", style = OTextStyle.Display)
            Spacer(modifier = Modifier.height(16.dp))
            OText(text = "원하는 카테고리가 없다면 건너뛸 수 있어요!", style = OTextStyle.Body2)
            Spacer(modifier = Modifier.height(32.dp))
            MatchCategoryGrid()
            Spacer(modifier = Modifier.weight(1f))
            OTextButton(text = "건너뛰기") {

            }
        }
    }
}