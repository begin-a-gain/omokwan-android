package com.begin_a_gain.feature.match.create_match

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.MatchCategoryGrid
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.button.OTextButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen

@Composable
fun MatchCategoryScreen(
    viewModel: CreateMatchViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
    navigateToCreateMatch: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    OScreen(
        bottomButtonText = "다음",
        bottomButtonType = if (state.selectedCategoryIndex == -1) ButtonType.Disable else ButtonType.Primary,
        onBottomButtonClick = navigateToCreateMatch,
        onBackButtonClick = navigateToMain
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            OText(
                modifier = Modifier.fillMaxWidth(),
                text = "어떤 대국인가요?",
                style = OTextStyle.Display
            )
            Spacer(modifier = Modifier.height(16.dp))
            OText(
                modifier = Modifier.fillMaxWidth(),
                text = "원하는 카테고리가 없다면 건너뛸 수 있어요!",
                style = OTextStyle.Body2
            )
            Spacer(modifier = Modifier.height(32.dp))
            MatchCategoryGrid(
                modifier = Modifier.fillMaxWidth(),
                selectedIndex = listOf(state.selectedCategoryIndex)
            ) {
                viewModel.setCategory(it)
            }
            Spacer(modifier = Modifier.weight(1f))
            OTextButton(
                text = "건너뛰기"
            ) {
                navigateToCreateMatch()
            }
        }
    }
}