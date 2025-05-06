package com.begin_a_gain.feature.match.match

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.begin_a_gain.domain.model.MemberHistory
import com.begin_a_gain.feature.match.match.util.MatchCalendarRow
import com.begin_a_gain.library.design.component.OHorizontalDivider
import com.begin_a_gain.library.design.component.OVerticalDivider
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.button.BottomModalButton
import com.begin_a_gain.library.design.component.button.ButtonStyle
import com.begin_a_gain.library.design.component.button.ButtonType
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.dialog.ODialog
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.text.InitialTextLayout
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.begin_a_gain.library.design.util.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MatchScreen() {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val calendarItemSize = (deviceWidth - 40.dp - 6.dp).div(6)

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMyProfileBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showOthersProfileBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showMemberOutDialog by rememberSaveable { mutableStateOf(false) }

    OScreen(
        title = "대국방 이름",
        showBackButton = true,
        trailingIcon = OImageRes.Menu,
        onTrailingIconClick = {
            // Todo : navigate to menu screen
        },
        useDefaultPadding = false,
        snackBarBottomPadding = 104.dp
    ) { showSnackBar ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MatchCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(12.dp)),
                itemSize = calendarItemSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            MatchMembers(
                itemWidth = calendarItemSize,
                onMemberClick = { index ->
                    if (index == 0) showMyProfileBottomSheet = true
                    else showOthersProfileBottomSheet = true
                },
                onAddMemberClick = {

                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            BottomModalButton(
                "오목두기"
            ) {
                showSnackBar("오늘의 오목두기를 완료하였습니다.")
            }
        }

        if (showMyProfileBottomSheet) {
            MemberProfileBottomSheet(
                sheetState = sheetState,
                isMine = true
            ) {
                showMyProfileBottomSheet = false
            }
        }

        if (showOthersProfileBottomSheet) {
            MemberProfileBottomSheet(
                sheetState = sheetState,
                isMine = false,
                isOwner = true,
                onSendOmokClick = {
                    // Todo : update
                    scope.launch {
                        showOthersProfileBottomSheet = false
                        delay(200L)
                        showSnackBar("‘0000’님에게 오목알을 튕겼습니다.")
                    }
                },
                onOutMemberClick = {
                    scope.launch {
                        showOthersProfileBottomSheet = false
                        delay(200L)
                        showMemberOutDialog = true
                    }
                }
            ) {
                showOthersProfileBottomSheet = false
            }
        }

        if (showMemberOutDialog) {
            ODialog(
                title = "이 멤버를 내보내시겠습니까?",
                message = "해당 멤버는 대국에 대한 모든 정보가 사라지며 복구할 수 없습니다.",
                buttonText = "내보내기",
                buttonType = ButtonType.Alert,
                onButtonClick = {
                    // Todo : update
                    scope.launch {
                        showMemberOutDialog = false
                        delay(200L)
                        showSnackBar("‘0000’님을 내보내셨습니다.")
                    }
                },
                additionalButtonText = "취소",
                onAdditionalButtonClick = {
                    showMemberOutDialog = false
                }
            ) {
               showMemberOutDialog = false
            }
        }
    }
}

@Preview
@Composable
fun CalendarStickyHeader(
    header: String = "2025. 4월",
    isSticky: Boolean = false
) {
    Box {
        Spacer(modifier = Modifier
            .background(ColorToken.UI_BG.color())
            .matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .run {
                    if (isSticky) {
                        this
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    } else this
                }
                .background(ColorToken.UI_02.color()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = header,
                style = OTextStyle.Title2,
                textAlign = TextAlign.Center
            )
            OVerticalDivider(
                modifier = Modifier.fillMaxWidth(),
                colorToken = ColorToken.STROKE_02,
                height = 2.dp
            )
        }
    }
}

fun getLastDayOfMonth(year: Int, month: Int): Int {
    val yearMonth = YearMonth(year, month)
    return yearMonth.toLocalDate(1).dayOfMonth().withMaximumValue().dayOfMonth
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MatchCalendar(
    modifier: Modifier = Modifier,
    itemSize: Dp = 58.dp
) {
    val startDate = DateTime.now()
    val startMonth = startDate.monthOfYear
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = modifier
    ) {
        (startMonth - 2..startMonth + 2).reversed().map { month ->
            stickyHeader {
                CalendarStickyHeader(
                    header = "${startDate.year}. ${month}월",
                    isSticky = true
                )
            }
            val days: List<Int> =
                (1..getLastDayOfMonth(startDate.year, month)).map { it }.reversed()
            items(days) { day ->
                MatchCalendarRow(
                    today = startDate.monthOfYear == month && startDate.dayOfMonth == day,
                    day = startDate.dayOfWeek().asText.take(1),
                    date = day,
                    size = itemSize
                )
                if (day == 1) {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MatchMembers(
    members: List<String> = listOf("준영", "생갈치1호의행방불명", "쥬짱", "연날리기"),
    itemWidth: Dp = 58.dp,
    onMemberClick: (Int) -> Unit = {},
    onAddMemberClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.width(itemWidth + 6.dp))
        (0..4).forEach { index ->
            if (index <= members.size) {
                Column(
                    modifier = Modifier
                        .width(itemWidth)
                        .padding(horizontal = 5.dp, vertical = 8.dp)
                ) {
                    if (index == members.size) {
                        AddMemberButton(
                            itemWidth = itemWidth
                        ) {
                            onAddMemberClick()
                        }
                    } else {
                        InitialTextLayout(
                            text = members[index],
                            itemWidth = itemWidth,
                            initialTextColor = if (index == 0) ColorToken.TEXT_ON_01 else ColorToken.TEXT_01,
                            backgroundColor = if (index == 0) ColorToken.UI_PRIMARY.color()
                            else if (index == members.size) Color.Transparent
                            else ColorToken.UI_03.color()
                        ) {
                            onMemberClick(index)
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.width(itemWidth))
            }
        }
    }
}

@Composable
fun AddMemberButton(
    itemWidth: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(itemWidth - 10.dp)
            .run {
                val stroke = Stroke(
                    width = 6f,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(
                            10f,
                            10f
                        ), 10f
                    )
                )
                drawBehind {
                    drawCircle(
                        color = AppColors.Gray400,
                        style = stroke
                    )
                }
            }
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        OImage(
            image = OImageRes.Plus,
            size = 24.dp,
            color = ColorToken.ICON_DISABLE.color()
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
    OText(
        modifier = Modifier.width(itemWidth),
        text = "멤버 추가",
        style = OTextStyle.Subtitle1,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = ColorToken.TEXT_DISABLE
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberProfileBottomSheet(
    sheetState: SheetState,
    isMine: Boolean = false,
    isOwner: Boolean = true,
    member: MemberHistory = MemberHistory(
        id = "",
        name = "가나다라",
        combo = 0,
        omok = 0,
        days = 0
    ),
    onSendOmokClick: () -> Unit = {},
    onOutMemberClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    OBottomSheet(
        title = (if (isMine) "나" else "${member.name} 님") + "의 프로필",
        sheetState = sheetState,
        heightRatio = null,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorToken.UI_BG.color())
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                InitialTextLayout(
                    text = "${member.name} 님",
                    itemWidth = 96.dp,
                    fullTextModifier = Modifier.fillMaxWidth(),
                    fullTextStyle = OTextStyle.Subtitle3,
                    verticalSpace = 10.dp,
                    isClickable = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (0..2).forEach { index ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OText(
                                style = OTextStyle.Title2,
                                text = when (index) {
                                    0 -> "${member.combo}"
                                    1 -> "${member.omok}"
                                    else -> "${member.days}"
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OText(
                                style = OTextStyle.Body1,
                                text = when (index) {
                                    0 -> "오목 콤포"
                                    1 -> "오목알"
                                    else -> "참여 일 수"
                                }
                            )
                        }
                        if (index != 2) {
                            OHorizontalDivider(
                                modifier = Modifier
                                    .height(12.dp)
                                    .padding(horizontal = 8.dp),
                                colorToken = ColorToken.STROKE_02
                            )
                        }
                    }
                }
            }

            if (isMine) {
                Spacer(modifier = Modifier.height(40.dp))
            } else {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    OButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "오목알 튕기기"
                    ) {
                        onSendOmokClick()
                    }

                    if (isOwner) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "내보내기",
                            style = ButtonStyle.None
                        ) {
                            onOutMemberClick()
                        }
                    }
                }
            }
        }
    }
}