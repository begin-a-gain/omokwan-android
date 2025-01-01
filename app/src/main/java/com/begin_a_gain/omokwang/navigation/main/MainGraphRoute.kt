package com.begin_a_gain.omokwang.navigation.main

import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.omokwang.navigation.MyPage
import com.begin_a_gain.omokwang.navigation.OmokList

data class MainGraphRoute<T: Any>(
    val name: String,
    val route: T,
    val icon: OImageRes
)

val bottomNavigationRoutes = listOf(
    MainGraphRoute("My 대국", OmokList, OImageRes.PlaceHolder),
    MainGraphRoute("마이페이지", MyPage, OImageRes.PlaceHolder),
)

enum class AddMatchType(
    val title: String,
    val image: Int
) {
    CreateMatch("대국 만들기", 0),
    JoinMatch("대국 참여하기", 0)
}