package com.begin_a_gain.feature.main.omoklist.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.domain.repository.model.OmokRoom
import com.begin_a_gain.library.core.constant.MAX_ROOM_MEMBER
import com.begin_a_gain.library.core.type.OmokRoomStatus
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.advanceShadow
import com.begin_a_gain.library.design.util.noRippleClickable

@Composable
fun OmokRoomItem(
    omokRoom: OmokRoom,
    size: Dp,
    onClickOmokRoom: () -> Unit,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        OImage(
            modifier = Modifier.size(size),
            image = OImageRes.OmokRoomGrid
        )

        if (omokRoom.status != OmokRoomStatus.None) {
            OImage(
                modifier = Modifier
                    .size(size)
                    .advanceShadow(
                        color = when (omokRoom.status) {
                            OmokRoomStatus.Todo,
                            OmokRoomStatus.Skip -> AppColors.OmokGrayShadow.copy(0.4f)

                            else -> ColorToken.UI_PRIMARY
                                .color()
                                .copy(0.3f)
                        },
                        borderRadius = 100.dp,
                        blurRadius = 20.dp,
                        spreadWidth = -20.dp,
                        spreadHeight = -20.dp
                    )
                    .noRippleClickable {
                        onClickOmokRoom()
                    },
                image = when (omokRoom.status) {
                    OmokRoomStatus.Todo,
                    OmokRoomStatus.Skip -> OImageRes.GrayOmokRoom

                    else -> OImageRes.PrimaryOmokRoom
                }
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OImage(
                    image = if (omokRoom.isLocked) OImageRes.Locked else OImageRes.Unlocked,
                    size = 16.dp
                )
                Spacer(modifier = Modifier.height(12.dp))
                OText(text = omokRoom.title, style = OTextStyle.Title2)
                Spacer(modifier = Modifier.height(6.dp))
                OText(text = "대국 +${omokRoom.date}일 째", style = OTextStyle.Caption)
                Spacer(modifier = Modifier.height(2.dp))
                OText(text = "${omokRoom.member}/$MAX_ROOM_MEMBER 명", style = OTextStyle.Caption)
            }

            OmokCheckButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                size = size / 192 * 64,
                status = omokRoom.status,
                onClickButton = onClickButton
            )
        }
    }
}

@Composable
private fun OmokCheckButton(
    size: Dp,
    modifier: Modifier,
    status: OmokRoomStatus,
    onClickButton: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                shape = CircleShape,
                color = when (status) {
                    OmokRoomStatus.Done -> ColorToken.UI_PRIMARY.color()
                    OmokRoomStatus.Todo -> ColorToken.UI_DISABLE_01.color()
                    else -> ColorToken.UI_DISABLE_02.color()
                }
            )
            .run {
                when (status) {
                    OmokRoomStatus.Todo -> {
                        val stroke = Stroke(
                            width = 10f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                        drawBehind {
                            drawCircle(
                                color = AppColors.Gray400,
                                style = stroke
                            )
                        }
                    }

                    else -> this
                }
            }
            .clickable { onClickButton() },
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            OmokRoomStatus.Done -> {
                OImage(
                    image = OImageRes.Checked,
                    size = 24.dp,
                    color = ColorToken.ICON_01.color()
                )
            }

            OmokRoomStatus.Todo -> {
                OText(
                    text = "완료하기",
                    style = OTextStyle.Subtitle1,
                    color = ColorToken.TEXT_ON_DISABLE
                )
            }

            else -> {
                OText(
                    text = "미완료",
                    style = OTextStyle.Subtitle1,
                    color = ColorToken.TEXT_01
                )
            }
        }
    }
}

@Preview
@Composable
fun OmokItemPreview() {
    Column(
        Modifier.background(Color.White)
    ) {
        OmokRoomStatus.entries.forEach { status ->
            OmokRoomItem(
                modifier = Modifier.size(192.dp),
                omokRoom = OmokRoom(
                    id = "",
                    status = status,
                    title = "아침 달리기 하기",
                    date = 1,
                    member = 1,
                    isLocked = false,
                ),
                size = 192.dp,
                onClickOmokRoom = {},
                onClickButton = {}
            )
        }
    }
}