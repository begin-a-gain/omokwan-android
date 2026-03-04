package com.begin_a_gain.feature.main.my_page.match_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.begin_a_gain.design.component.OHorizontalDivider
import com.begin_a_gain.design.component.Skeleton
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.domain.model.user.MyPageMatchItem

@Preview
@Composable
fun MyMatchListFullPopup(
    isComplete: Boolean = false,
    matchList: List<MyPageMatchItem> = emptyList(),
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        OScreen(
            title = if (isComplete) "완료한 대국" else "진행 중인 대국",
            useDefaultPadding = false,
            onBackButtonClick = {
                onDismissRequest()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorToken.UI_02.color())
                    .padding(20.dp)
            ) {
                LazyColumn {
                    items(matchList.size) { index ->
                        MyMatchListItem(
                            match = matchList[index],
                            isLoading = false,
                            isFirst = index == 0,
                            isLast = index == matchList.size - 1,
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyMatchListItem(
    match: MyPageMatchItem = MyPageMatchItem(),
    isLoading: Boolean = true,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .run {
                if (isFirst) {
                    clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                } else if (isLast) {
                    clip(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                } else {
                    this
                }
            }
            .clickable(onClick = onClick)
            .background(ColorToken.UI_BG.color())
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Skeleton(
                isLoading = isLoading
            ){
                OText(
                    text = match.title,
                    style = OTextStyle.Title2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Skeleton(
                isLoading = isLoading
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OText(
                        text = "대국 참여 일 수  +${match.ongoingDays}",
                        style = OTextStyle.Caption
                    )
                    OHorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .height(12.dp), colorToken = ColorToken.STROKE_02
                    )
                    OText(
                        text = "콤보 ${match.combo}",
                        style = OTextStyle.Caption
                    )
                    OHorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .height(12.dp), colorToken = ColorToken.STROKE_02
                    )
                    OText(
                        text = "오목알 ${match.omok}",
                        style = OTextStyle.Caption
                    )
                }
            }
        }
    }
}