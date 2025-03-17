package com.begin_a_gain.feature.match.join_match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.selection.OChip
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.begin_a_gain.library.design.util.oDefaultPadding

@Preview
@Composable
fun JoinMatchScreen() {
    OScreen(
        title = "대국 참여하기",
        useDefaultPadding = false
    ) {
        Column {
            JoinMatchHeader("", 0)
            JoinMatchList()
        }
    }
}

@Composable
fun JoinMatchHeader(
    keyword: String = "",
    selectedCategoryCount: Int
) {
    Column(
        modifier = Modifier.oDefaultPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorToken.UI_03.color(), shape = RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OImage(image = OImageRes.Search)
            Spacer(modifier = Modifier.width(4.dp))
            if (keyword.isEmpty()) {
                OText(
                    text = "대국 이름, 대국방 ID, 방장으로 검색하기",
                    style = OTextStyle.Body1,
                    color = ColorToken.TEXT_02
                )
            } else {
                BasicTextField(value = keyword, onValueChange = {})
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OChip(
                text = "참여 가능한 방",
                isSelected = false
            ) {

            }

            OChip(
                text = "카테고리${if (selectedCategoryCount != 0) " $selectedCategoryCount" else ""}",
                isSelected = selectedCategoryCount != 0,
                trailingContent = {
                    Spacer(modifier = Modifier.width(4.dp))
                    OImage(
                        image = OImageRes.ArrowDown,
                        size = 16.dp,
                        color = if (selectedCategoryCount != 0) ColorToken.ICON_PRIMARY.color()
                                else ColorToken.ICON_01.color()
                    )
                }
            ) {

            }
        }
    }
}

@Preview
@Composable
fun JoinMatchList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorToken.UI_02.color())
            .padding(20.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorToken.UI_BG.color(), shape = RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JoinMatchItem(
                title = "테스트 1",
                isPrivate = true,
                maximumParticipants = 5,
                currentParticipants = 5,
                category = "테스트",
                date = 10,
                ownerName = "junyoungleee",
                alreadyJoined = true
            )

            JoinMatchItem(
                title = "테스트 2",
                isPrivate = false,
                maximumParticipants = 5,
                currentParticipants = 3,
                category = "테스트",
                date = 10,
                ownerName = "junyoungleee",
                alreadyJoined = true
            )

            JoinMatchItem(
                title = "테스트 3",
                isPrivate = true,
                maximumParticipants = 5,
                currentParticipants = 5,
                category = "테스트",
                date = 10,
                ownerName = "junyoungleee",
                alreadyJoined = false
            )

            JoinMatchItem(
                title = "테스트 4",
                isPrivate = false,
                maximumParticipants = 5,
                currentParticipants = 3,
                category = "테스트",
                date = 10,
                ownerName = "junyoungleee",
                alreadyJoined = false
            )
        }
    }
}