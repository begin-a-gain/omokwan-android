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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.begin_a_gain.design.component.dialog.ProgressBar
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
import com.begin_a_gain.domain.model.user.User

@Composable
fun InviteMemberScreen(
    maxParticipants: Int = 5,
    currentMembers: List<Int> = emptyList(),
    viewModel: InviteMemberViewModel = hiltViewModel(),
    navigateToSetting: (List<User>) -> Unit = {}
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val users = viewModel.usersPagingData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.initialize(maxParticipants, currentMembers)
    }

    if (users.loadState.refresh is LoadState.Loading) {
        ProgressBar()
    }

    OScreen(
        title = "대국 초대하기",
        showBackButton = true,
        onBackButtonClick = {
            navigateToSetting(state.newMembers)
        },
        bottomButtonUiType = ScreenBottomButtonType.Modal,
        bottomButtonText = "초대하기",
        useDefaultPadding = false,
        onBottomButtonClick = {
            navigateToSetting(state.newMembers)
        }
    ) {
        Column {
            SelectedInvitees(
                invitees = state.newMembers,
                onDelete = { index ->
                    viewModel.selectNewMember(state.newMembers[index])
                }
            )
            SearchBar(
                modifier = Modifier.padding(20.dp),
                keyword = state.searchQuery,
                hint = "이름으로 검색하기"
            ) {
                viewModel.onSearchQueryChanged(it)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(ColorToken.UI_02.color()),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(
                    count = users.itemCount
                ) { index ->
                    val user = users[index]
                    if (user != null) {
                        InviteeItem(
                            name = user.nickname,
                            isSelected = state.newMembers.any { it.userId == user.userId },
                            isFirst = index == 0,
                            isLast = index == users.itemCount - 1
                        ) {
                            viewModel.selectNewMember(user)
                        }
                    }
                }
            }
        }
    }
}

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

@Composable
fun SelectedInvitees(
    invitees: List<User> = emptyList(),
    onDelete: (Int) -> Unit = {}
) {
    val scroll = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .horizontalScroll(scroll),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        invitees.forEach { user ->
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .noRippleClickable {
                        onDelete(user.userId)
                    },
                contentAlignment = Alignment.TopEnd
            ) {
                InitialTextLayout(
                    modifier = Modifier.padding(top = 2.dp),
                    text = user.nickname,
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
