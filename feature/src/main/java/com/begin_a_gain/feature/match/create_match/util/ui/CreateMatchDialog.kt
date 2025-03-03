package com.begin_a_gain.feature.match.create_match.util.ui

import androidx.compose.runtime.Composable
import com.begin_a_gain.library.design.component.dialog.ODialog

@Composable
fun CreateMatchDialog(
    matchTitle: String,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ODialog(
        title = "대국 생성 하시겠습니까?",
        message = "\'$matchTitle\' 대국을 시작해보세요.",
        buttonText = "확인",
        onButtonClick = onConfirmClick,
        additionalButtonText = "취소",
        onAdditionalButtonClick = onDismissRequest
    ) {
        onDismissRequest()
    }
}

@Composable
fun LeaveCreateMatchDialog(
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ODialog(
        title = "저장하지 않고 나가시겠습니까?",
        message = "모든 입력사항이 사라집니다.\n대국 시작하기를 눌러 저장해주세요.",
        buttonText = "나가기",
        onButtonClick = onConfirmClick,
        additionalButtonText = "닫기",
        onAdditionalButtonClick = onDismissRequest
    ) {
        onDismissRequest()
    }
}