package com.begin_a_gain.feature.match.join_match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.begin_a_gain.feature.match.common.MatchCategoryGrid
import com.begin_a_gain.feature.match.common.MatchCodeDialog
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.dialog.ODialog
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.selection.OChip
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.OScreen
import com.begin_a_gain.library.design.util.oDefaultPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun JoinMatchScreen(
    viewModel: JoinMatchViewModel = hiltViewModel()
) {
    OScreen(
        title = "대국 참여하기",
        useDefaultPadding = false
    ) {
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(true)

        val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
        var keyword by rememberSaveable { mutableStateOf("") }
        var showCategoryBottomSheet by rememberSaveable { mutableStateOf(false) }
        var showJoinMatchDialog by rememberSaveable { mutableStateOf(false) }
        var showMatchCodeDialog by rememberSaveable { mutableStateOf(false) }
        var selectedMatch by remember {
            mutableStateOf<MatchInfo?>(null)
        }

        Column {
            JoinMatchHeader(
                keyword = keyword,
                onKeywordChanged = { keyword = it },
                selectedCategoryCount = state.categoryFilter.size,
                onAvailableMatchChipClick = {
                    viewModel.setAvailableMatchFilter()
                },
                onCategoryChipClick = {
                    showCategoryBottomSheet = true
                }
            )
            JoinMatchList(
                matchItems = state.matchList
            ) { match ->
                selectedMatch = match
                if (!match.alreadyJoined && match.maximumParticipants != match.currentParticipants) {
                    showJoinMatchDialog = true
                }
            }
        }

        if (showCategoryBottomSheet) {
            CategoryFilterBottomSheet(
                sheetState = bottomSheetState,
                selectedIndexList = state.categoryFilter,
                onDismissRequest = { showCategoryBottomSheet = false }
            ) {
                showCategoryBottomSheet = false
                viewModel.setCategoryFilter(it)
            }
        }

        if (showJoinMatchDialog) {
            selectedMatch?.let {
                ODialog(
                    title = "대국에 참여하시겠습니까?",
                    message = "\'${it.title}\' 대국을 시작해보세요.",
                    buttonText = "참여",
                    onButtonClick = { /*TODO*/ },
                    additionalButtonText = "취소",
                    onAdditionalButtonClick = {
                        scope.launch {
                            showJoinMatchDialog = false
                            delay(250L)
                            showMatchCodeDialog = true
                        }
                    }
                ) {
                    showJoinMatchDialog = false
                }
            }
        }

        if (showMatchCodeDialog) {
            MatchCodeDialog(
                onConfirmClick = {},
                onCancelClick = {
                    showMatchCodeDialog = false
                }
            ) {
                showMatchCodeDialog = false
            }
        }
    }
}

@Composable
fun JoinMatchHeader(
    keyword: String = "",
    onKeywordChanged: (String) -> Unit,
    selectedCategoryCount: Int,
    onAvailableMatchChipClick: () -> Unit,
    onCategoryChipClick: () -> Unit
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
            Box(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    value = keyword,
                    onValueChange = onKeywordChanged,
                    singleLine = true
                )
                if (keyword.isEmpty()) {
                    OText(
                        text = "대국 이름, 대국방 ID, 방장으로 검색하기",
                        style = OTextStyle.Body1,
                        color = ColorToken.TEXT_02
                    )
                }
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
                onAvailableMatchChipClick()
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
                onCategoryChipClick()
            }
        }
    }
}

@Preview
@Composable
fun JoinMatchList(
    matchItems: List<MatchInfo> = testMatchList,
    onJoinMatchClick: (MatchInfo) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorToken.UI_02.color())
            .padding(
                start = 20.dp,
                end = 20.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorToken.UI_BG.color(), shape = RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = matchItems
            ) { index, match ->
                JoinMatchItem(
                    match = match,
                    isFirst = index == 0,
                    isLast = index == testMatchList.size - 1
                ) {
                    onJoinMatchClick(match)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterBottomSheet(
    sheetState: SheetState,
    selectedIndexList: List<Int>,
    onDismissRequest: () -> Unit,
    onSelected: (List<Int>) -> Unit
) {
    var currentIndexList by rememberSaveable { mutableStateOf(selectedIndexList) }

    OBottomSheet(
        title = "대국 카테고리",
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            MatchCategoryGrid(
                modifier = Modifier.oDefaultPadding(),
                selectedIndex = currentIndexList
            ) {
                if (currentIndexList.contains(it)) {
                    val newList = currentIndexList.filter { index -> index != it }
                    currentIndexList = newList
                } else {
                    val newList = currentIndexList + listOf(it)
                    currentIndexList = newList
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OButton(
                modifier = Modifier
                    .oDefaultPadding()
                    .fillMaxWidth(),
                text = "확인"
            ) {
                onSelected(currentIndexList)
            }
        }
    }
}