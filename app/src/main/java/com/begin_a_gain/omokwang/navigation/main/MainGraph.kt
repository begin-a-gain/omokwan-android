package com.begin_a_gain.omokwang.navigation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.begin_a_gain.feature.main.MyPageScreen
import com.begin_a_gain.feature.main.match_list.OmokMatchListScreen
import com.begin_a_gain.library.design.component.bottom_sheet.OBottomSheet
import com.begin_a_gain.library.design.component.button.OButton
import com.begin_a_gain.library.design.component.button.OIconButton
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.library.design.component.image.OImageRes
import com.begin_a_gain.library.design.component.text.OText
import com.begin_a_gain.library.design.theme.AppColors
import com.begin_a_gain.library.design.theme.ColorToken
import com.begin_a_gain.library.design.theme.ColorToken.Companion.color
import com.begin_a_gain.library.design.theme.OTextStyle
import com.begin_a_gain.library.design.util.advanceShadow
import com.begin_a_gain.library.design.util.noRippleClickable
import com.begin_a_gain.omokwang.navigation.MatchList
import com.begin_a_gain.omokwang.navigation.MyPage

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun MainGraph(
    navigateToCreateMatch: () -> Unit = {},
    navigateToJoinMatch: () -> Unit = {}
) {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(true)
    var showAddMatchBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            OIconButton(
                modifier = Modifier.advanceShadow(
                    color = ColorToken.UI_BG.color(),
                    borderRadius = 100.dp,
                    blurRadius = 20.dp,
                ),
                icon = OImageRes.Plus,
                iconSize = 32.dp,
                iconColor = AppColors.White,
                backgroundColor = ColorToken.UI_PRIMARY,
                size = 64.dp
            ) {
                showAddMatchBottomSheet = true
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(80.dp)
                    .advanceShadow(
                        color = ColorToken.UI_PRIMARY
                            .color()
                            .copy(0.2f),
                        blurRadius = 20.dp,
                    ),
                backgroundColor = ColorToken.UI_BG.color(),
                cutoutShape = CircleShape
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                bottomNavigationRoutes.forEach { bottomNavigation ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.hasRoute(bottomNavigation.route::class) } == true

                    if (bottomNavigation.route == MyPage) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                            .weight(1f)
                            .noRippleClickable {
                                navController.navigate(bottomNavigation.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OImage(
                            image = bottomNavigation.icon,
                            size = 24.dp,
                            color = if (isSelected) {
                                ColorToken.ICON_01.color()
                            } else {
                                ColorToken.ICON_DISABLE.color()
                            }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OText(
                            text = bottomNavigation.name,
                            style = OTextStyle.Title1,
                            color = if (isSelected) {
                                ColorToken.TEXT_01
                            } else {
                                ColorToken.TEXT_DISABLE
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    if (bottomNavigation.route == MatchList) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MatchList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MatchList> {
                OmokMatchListScreen()
            }
            composable<MyPage> {
                MyPageScreen()
            }
        }

        if (showAddMatchBottomSheet) {
            AddMatchBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showAddMatchBottomSheet = false }
            ) { type ->
                when (type) {
                    AddMatchType.CreateMatch -> {
                        navigateToCreateMatch()
                    }
                    AddMatchType.JoinMatch -> {
                        navigateToJoinMatch()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMatchBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onSelect: (AddMatchType) -> Unit
) {
    var selectedType by rememberSaveable { mutableStateOf(AddMatchType.CreateMatch) }

    OBottomSheet(
        title = "대국 추가하기",
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            Row(
                modifier = Modifier.weight(1f).padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AddMatchType.entries.forEach { type ->
                    val isSelected = selectedType == type
                    Column(
                        modifier = Modifier.weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) {
                                    ColorToken.STROKE_PRIMARY.color()
                                } else {
                                    ColorToken.STROKE_02.color()
                                }
                            )
                            .padding(24.dp)
                            .noRippleClickable {
                                selectedType = type
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(ColorToken.UI_DISABLE_01.color())
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OText(
                            text = type.title,
                            style = OTextStyle.Title2,
                            color = if (isSelected) ColorToken.TEXT_01 else ColorToken.TEXT_02
                        )
                    }
                }
            }

            OButton(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                text = "확인"
            ) {
                onDismissRequest()
                onSelect(selectedType)
            }
        }
    }
}