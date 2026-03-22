package com.begin_a_gain.feature.main.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.design.component.OListLazyColumn
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.button.OTextButton
import com.begin_a_gain.design.component.listItemBackground
import com.begin_a_gain.design.component.selection.OChip
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OPreview
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.noRippleClickable
import com.begin_a_gain.domain.enum.NotificationType
import com.begin_a_gain.domain.model.Notification
import org.joda.time.DateTime

@Preview
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    OScreen(
        title = "알림"
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NotificationControls(
                filter = state.filter,
                unreadCount = state.notifications.count { !it.isRead },
                onSelectFilter = {

                },
                onClickReadAll = {

                }
            )

            OListLazyColumn(
                modifier = Modifier.weight(1f)
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun NotificationControls(
    filter: NotificationFilter = NotificationFilter.All,
    unreadCount: Int = 1,
    onSelectFilter: (NotificationFilter) -> Unit = {},
    onClickReadAll: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OChip(
            text = "전체",
            isSelected = filter == NotificationFilter.All,
            onSelect = {
                onSelectFilter(NotificationFilter.All)
            }
        )
        Spacer(Modifier.width(8.dp))
        OChip(
            text = "안 읽은 알림${if (unreadCount > 0) " $unreadCount" else ""}",
            isSelected = filter == NotificationFilter.Unread,
            onSelect = {
                onSelectFilter(NotificationFilter.Unread)
            }
        )
        Spacer(Modifier.weight(1f))
        OText(
            modifier = Modifier.noRippleClickable {
                onClickReadAll()
            },
            text = "모두 읽기",
            style = OTextStyle.Subtitle2,
            color = ColorToken.TEXT_PRIMARY
        )
    }
}

@Composable
private fun NotificationItem(
    notification: Notification,
    isFirst: Boolean,
    isLast: Boolean,
    onClickNotification: () -> Unit = {},
    onClickParticipate: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .listItemBackground(isFirst = isFirst, isLast = isLast)
            .clickable(notification.type != NotificationType.MATCH_INVITED) {
                onClickNotification()
            }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            OText(
                text = when (notification.type) {
                    NotificationType.MATCH_INVITED -> "${notification.matchName} 대국 초대"
                    NotificationType.MATCH_JOINED -> "${notification.matchName} 대국 멤버 참여"
                    NotificationType.MEMBER_LEFT -> "${notification.matchName} 대국 멤버 탈퇴"
                    NotificationType.HOST_CHANGED -> "${notification.matchName} 대국장 변경"
                },
                style = OTextStyle.Subtitle2,
                color = ColorToken.TEXT_02
            )
            Spacer(Modifier.height(8.dp))
            OText(
                text = when (notification.type) {
                    NotificationType.MATCH_INVITED -> "대국에 초대되었어요. 참여해볼까요?"
                    NotificationType.MATCH_JOINED -> "${notification.actorNickname}님이 대국에 참여했어요."
                    NotificationType.MEMBER_LEFT -> "${notification.actorNickname}님이 대국에서 나갔어요."
                    NotificationType.HOST_CHANGED -> "대국장이 ${notification.prevHostNickname}님에서 ${notification.newHostNickname}님으로 변경되었어요."
                },
                style = OTextStyle.Title2,
                color = ColorToken.TEXT_01
            )
            Spacer(Modifier.height(8.dp))
            OText(
                text = when (notification.diffInMinutes) {
                    0 -> "방금 전"
                    in 1..59 -> "${notification.diffInMinutes}분 전"
                    in 60 until 60 * 24 -> "${notification.diffInMinutes / 60}시간 전"
                    in 60 * 24 until 60 * 24 * 7 -> "${notification.diffInMinutes / (60 * 24)}일 전"
                    else -> {
                        val date = DateTime.now().plusDays(notification.diffInMinutes / (60 * 24))
                        "${date.monthOfYear}월 ${date.dayOfMonth}일"
                    }
                },
                style = OTextStyle.Subtitle2,
                color = ColorToken.TEXT_02
            )
            if (notification.type == NotificationType.MATCH_INVITED) {
                Spacer(Modifier.height(12.dp))
                OButton(
                    text = " 참여하기 "
                ) {
                    onClickParticipate()
                }
            }
        }
        if (!notification.isRead) {
            Spacer(Modifier.width(32.dp))
            Spacer(
                Modifier
                    .size(8.dp)
                    .background(
                        color = ColorToken.UI_PRIMARY.color(),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun NotificationItemPreview() {
    val notificationSamples = listOf(
        Notification(
            notificationId = 1,
            type = NotificationType.MATCH_INVITED,
            diffInMinutes = 5, // 5분 전
            isRead = false,
            matchId = 1001,
            matchName = "강남 금요 풋살 8인",
            isPublic = true,
            actorNickname = "손흥민",
            prevHostNickname = "",
            newHostNickname = ""
        ),
        Notification(
            notificationId = 2,
            type = NotificationType.MATCH_JOINED,
            diffInMinutes = 30, // 30분 전
            isRead = true,
            matchId = 1002,
            matchName = "토요일 오전 테니스 복식",
            isPublic = true,
            actorNickname = "이강인",
            prevHostNickname = "",
            newHostNickname = ""
        ),
        Notification(
            notificationId = 3,
            type = NotificationType.MEMBER_LEFT,
            diffInMinutes = 120, // 2시간 전
            isRead = false,
            matchId = 1001,
            matchName = "강남 금요 풋살 8인",
            isPublic = true,
            actorNickname = "김민재",
            prevHostNickname = "",
            newHostNickname = ""
        ),
        Notification(
            notificationId = 4,
            type = NotificationType.HOST_CHANGED,
            diffInMinutes = 600, // 10시간 전
            isRead = true,
            matchId = 1005,
            matchName = "일요일 저녁 배드민턴",
            isPublic = false,
            actorNickname = "시스템",
            prevHostNickname = "박지성",
            newHostNickname = "황희찬"
        )
    )
    OPreview {
        Column(
            modifier = Modifier
                .background(ColorToken.UI_02.color())
                .padding(20.dp)
        ) {
            notificationSamples.forEachIndexed { index, notification ->
                NotificationItem(
                    notification = notification,
                    isFirst = index == 0,
                    isLast = index == notificationSamples.lastIndex
                )
            }
        }
    }
}