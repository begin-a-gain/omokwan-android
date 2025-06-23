package com.begin_a_gain.design.component.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.design.component.button.OButton
import com.begin_a_gain.design.component.selection.OPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OPickerBottomSheet(
    title: String,
    items: List<String>,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    buttonText: String = "확인",
    onDismissRequest: () -> Unit,
    onSelect: (Int) -> Unit
) {
    val pickerState = rememberLazyListState(selectedIndex)
    val currentIndex by remember {
        derivedStateOf { pickerState.firstVisibleItemIndex }
    }

    OBottomSheet(
        title = title,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        heightRatio = 0.5,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            OPicker(
                modifier = Modifier.height(200.dp),
                items = items,
                lazyListState = pickerState
            )
            Spacer(modifier = Modifier.weight(1f))
            OButton(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                text = buttonText
            ) {
                onSelect(currentIndex)
                onDismissRequest()
            }
        }
    }
}