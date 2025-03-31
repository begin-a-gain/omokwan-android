package com.begin_a_gain.feature.match.create_match.util.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.button.ButtonStyle
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.OTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPermissionBottomSheet(
    sheetState: SheetState,
    onAgreeClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    OBottomSheet(
        title = "",
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OText(text = "기기의 알림 설정을 켜주세요", style = OTextStyle.Headline)
            Spacer(modifier = Modifier.height(30.dp))
            Spacer(modifier = Modifier.size(300.dp, 160.dp))
        }
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OButton(
                modifier = Modifier.fillMaxWidth(),
                text = "동의하기"
            ) {
                onAgreeClick()
            }
            OButton(
                modifier = Modifier.fillMaxWidth(),
                text = "다음에",
                style = ButtonStyle.None
            ) {
                onDismissRequest()
            }
        }
    }
}