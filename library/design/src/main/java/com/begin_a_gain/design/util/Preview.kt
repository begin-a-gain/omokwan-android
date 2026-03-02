package com.begin_a_gain.design.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@Composable
fun OPreview(
    modifier: Modifier = Modifier,
    verticalSpace: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPreview(
    skipPartiallyExpanded: Boolean = false,
    content: @Composable (SheetState, CoroutineScope) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded)
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content(sheetState, scope)
    }
}