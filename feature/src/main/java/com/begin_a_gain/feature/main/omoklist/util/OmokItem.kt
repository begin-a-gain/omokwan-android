package com.begin_a_gain.feature.main.omoklist.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes

@Composable
fun OmokItem(
    status: OmokStatus,
    isLocked: Boolean,
    title: String,
    date: Int,
    member: Int,
    onClickOmok: (Int) -> Unit,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        OImage(
            modifier = Modifier.matchParentSize(),
            image = when (status) {
                OmokStatus.None -> OImageRes.EmptyOmokRoom

                OmokStatus.Todo,
                OmokStatus.Skip -> OImageRes.GrayOmokRoom

                OmokStatus.Done -> OImageRes.PrimaryOmokRoom
            }
        )

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

        }
    }
}