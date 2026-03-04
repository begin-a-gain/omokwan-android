package com.begin_a_gain.feature.main.my_page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.begin_a_gain.design.component.image.OImage
import com.begin_a_gain.design.component.image.OImageRes
import com.begin_a_gain.design.component.text.OText
import com.begin_a_gain.design.theme.ColorToken
import com.begin_a_gain.design.theme.ColorToken.Companion.color
import com.begin_a_gain.design.theme.OTextStyle
import com.begin_a_gain.design.util.OScreen
import com.begin_a_gain.design.util.noRippleClickable
import com.begin_a_gain.feature.main.my_page.change_nickname.ChangeNicknameFullPopup

@Preview
@Composable
fun MyPageScreen(
    viewModel: MyPageListViewModel = hiltViewModel()
) {
    val scroll = rememberScrollState()
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    var showChangeNicknameDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initiate()
    }

    OScreen(
        title = "마이페이지",
        useDefaultPadding = false,
        snackBarBottomPadding = 54.dp
    ) { showSnackBar ->
        Column(
            modifier = Modifier.verticalScroll(scroll),
        ) {
            MyPageHeader(
                userName = state.nickname
            ) {
                showChangeNicknameDialog = true
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorToken.UI_01.color())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyPageTable(
                    title = "나의 대국",
                    items = listOf(
                        MyPageTableItem(
                            subTitle = "진행 중인 대국",
                            description = "${state.inProgressMatches.size}",
                            onClick = {

                            }
                        ),
                        MyPageTableItem(
                            subTitle = "완료한 대국",
                            description = "${state.completedMatches.size}",
                            onClick = {

                            }
                        )
                    )
                )

                MyPageTable(
                    title = "일반",
                    items = listOf(
                        MyPageTableItem(
                            subTitle = "알림",
                            onClick = {

                            }
                        )
                    )
                )

                MyPageTable(
                    title = "정보",
                    items = listOf(
                        MyPageTableItem(
                            subTitle = "현재 앱 버전",
                            description = "v1.0.0"
                        ),
                        MyPageTableItem(
                            subTitle = "이용약관",
                            onClick = {

                            }
                        ),
                        MyPageTableItem(
                            subTitle = "개인정보처리방침",
                            onClick = {

                            }
                        )
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OText(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
                        text = "로그아웃",
                        style = OTextStyle.Title2
                    )
                    Spacer(
                        Modifier
                            .size(height = 16.dp, width = 2.dp)
                            .background(color = ColorToken.STROKE_03.color())
                    )
                    OText(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
                        text = "회원탈퇴",
                        style = OTextStyle.Title2
                    )
                }

                Spacer(Modifier.height(60.dp))
            }
        }

        if (showChangeNicknameDialog) {
            ChangeNicknameFullPopup(nickname = state.nickname) { isSaved ->
                showChangeNicknameDialog = false
                if (isSaved) {
                    showSnackBar("닉네임이 변경 되었어요.")
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPageHeader(
    userName: String = "가나다라마바사",
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorToken.UI_BG.color())
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .noRippleClickable() {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Spacer(
                Modifier
                    .size(86.dp)
                    .background(
                        color = ColorToken.UI_PRIMARY.color().copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = ColorToken.STROKE_PRIMARY.color(),
                        shape = CircleShape
                    )
            )
            OText(
                text = "${userName.firstOrNull()?: ""}",
                style = OTextStyle.Display2,
                color = ColorToken.TEXT_PRIMARY
            )
        }
        Spacer(Modifier.width(10.dp))
        Box(modifier = Modifier.width(IntrinsicSize.Max)) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .align(Alignment.BottomCenter)
                    .background(ColorToken.STROKE_PRIMARY_OP40.color())
            )
            OText(
                text = "$userName 님",
                style = OTextStyle.Headline
            )
        }
        Spacer(Modifier.width(4.dp))
        OImage(
            modifier = Modifier.size(16.dp),
            image = OImageRes.ArrowRight
        )
    }
}

@Composable
fun MyPageTable(
    title: String,
    items: List<MyPageTableItem>
) {
    Column(
        modifier = Modifier
            .background(
                color = ColorToken.UI_BG.color(),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        OText(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 12.dp),
            text = title,
            style = OTextStyle.Title2
        )
        items.forEach { item ->
            Row(
                modifier = Modifier.padding(vertical = 22.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OText(
                    modifier = Modifier.weight(1f),
                    text = item.subTitle,
                    style = OTextStyle.Subtitle3
                )
                Spacer(Modifier.width(8.dp))
                OText(
                    modifier = Modifier.wrapContentWidth(),
                    text = item.description,
                    style = OTextStyle.Body2,
                    color = ColorToken.TEXT_02
                )
                if (item.onClick != null) {
                    Spacer(Modifier.width(8.dp))
                    OImage(
                        modifier = Modifier.size(16.dp),
                        image = OImageRes.ArrowRight,
                        color = ColorToken.ICON_02.color()
                    )
                }
            }
        }
    }
}

data class MyPageTableItem(
    val subTitle: String,
    val description: String = "",
    val onClick: (() -> Unit)? = null
)