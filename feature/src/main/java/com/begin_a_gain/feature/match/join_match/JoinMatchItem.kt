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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.OHorizontalDivider
import com.begin_a_gain.design.component.button.ButtonSize
import com.begin_a_gain.design.component.button.ButtonType
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.domain.model.match.MatchInfo

@Composable
fun JoinMatchItem(
    match: MatchInfo,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    onJoinMatchClick: () -> Unit = {}
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
            .background(ColorToken.UI_BG.color())
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                OImage(
                    image = if (match.public) OImageRes.Unlocked else OImageRes.Locked,
                    size = 16.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                OText(text = match.name, style = OTextStyle.Title2)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OText(
                    text = "${match.participants}/${match.maxParticipants} 명",
                    style = OTextStyle.Caption
                )
                OHorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .height(12.dp), colorToken = ColorToken.STROKE_02
                )
                match.category?.let { category ->
                    OText(text = "#${category.name}", style = OTextStyle.Caption)
                    OHorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .height(12.dp), colorToken = ColorToken.STROKE_02
                    )
                }
                OText(text = "대국 +${match.ongoingDays}일 째", style = OTextStyle.Caption)
            }

            OText(text = "${match.owner}님의 방", style = OTextStyle.Caption)
        }

        Spacer(modifier = Modifier.weight(1f))
        OButton(
            text = if (match.joinable) "참여하기" else "참여중",
            type = if (!match.joinable && match.maxParticipants != match.participants) ButtonType.Primary else ButtonType.Disable,
            size = ButtonSize.Medium
        ) {
            onJoinMatchClick()
        }
    }
}