package com.begin_a_gain.feature.match.invite_member

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.button.BottomModalButton
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.InitialText
import com.begin_a_gain.design.component.text.InitialTextLayout
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.component.text.SearchBar
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.ScreenBottomButtonType
import com.begin_a_gain.design.util.noRippleClickable

@Preview
@Composable
fun InviteMemberScreen() {
    OScreen(
        title = "대국 초대하기",
        showBackButton = true,
        onBackButtonClick = {

        },
        bottomButtonUiType = ScreenBottomButtonType.Modal,
        bottomButtonText = "초대하기",
        onBottomButtonClick = {

        }
    ) {
        Column {
            SelectedInvitees()
            SearchBar(
                modifier = Modifier.padding(vertical = 20.dp),
                keyword = "",
                hint = "이름으로 검색하기"
            ) {

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(ColorToken.UI_02.color()),
                contentPadding = PaddingValues(20.dp)
            ) {
                itemsIndexed(
                    (0..10).map { "Member $it" }
                ) { index, it ->
                    InviteeItem(
                        name = it,
                        isSelected = false,
                        isFirst = index == 0,
                        isLast = index == 10
                    ) {

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InviteeItem(
    name: String = "가나다라",
    isSelected: Boolean = false,
    isFirst: Boolean = true,
    isLast: Boolean = false,
    onSelect: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .run {
                if (isFirst)
                    this.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                else if (isLast)
                    this.clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                else this
            }
            .background(ColorToken.UI_BG.color())
            .clickable { onSelect() }
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialText(
            text = name,
            itemWidth = 42.dp,
            initialTextStyle = OTextStyle.Title2,
            isClickable = false
        ) { }
        Spacer(modifier = Modifier.width(8.dp))
        OText(modifier = Modifier.weight(1f), text = name, style = OTextStyle.Subtitle1)
        Spacer(modifier = Modifier.width(32.dp))
        OImage(
            image = OImageRes.Checked,
            color = if (isSelected) null else ColorToken.STROKE_DISABLE.color()
        )
    }
}

@Preview
@Composable
fun SelectedInvitees(
    invitees: List<String> = (0..2).map { "Member $it" },
    onDelete: (Int) -> Unit = {}
) {
    val scroll = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .horizontalScroll(scroll),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        invitees.forEachIndexed { index, name ->
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .noRippleClickable {
                        onDelete(index)
                    },
                contentAlignment = Alignment.TopEnd
            ) {
                InitialTextLayout(
                    modifier = Modifier.padding(top = 2.dp),
                    text = name,
                    itemWidth = 58.dp,
                    isClickable = false
                )

                Box(
                    modifier = Modifier
                        .background(
                            shape = CircleShape,
                            color = ColorToken.UI_BG2.color()
                        )
                        .size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OImage(
                        image = OImageRes.Cancel,
                        size = 16.dp,
                        color = ColorToken.ICON_ON_01.color()
                    )
                }
            }
        }

    }
}