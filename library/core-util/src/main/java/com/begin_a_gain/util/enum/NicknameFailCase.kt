package com.begin_a_gain.util.enum

enum class NicknameFailCase(val message: String) {
    Duplicated("이미 사용중인 아이디 입니다."),
    Unconventional("2~10글자 사이의 한글 혹은 영문만 사용해주세요.")
}