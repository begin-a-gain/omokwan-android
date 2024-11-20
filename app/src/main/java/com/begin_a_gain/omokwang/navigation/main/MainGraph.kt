package com.begin_a_gain.omokwang.navigation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.begin_a_gain.feature.main.OmokListScreen
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
import com.begin_a_gain.omokwang.navigation.MyPage
import com.begin_a_gain.omokwang.navigation.OmokList

@Preview(showSystemUi = true)
@Composable
fun MainGraph(

) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            OIconButton(
                modifier = Modifier.advanceShadow(
                    color = ColorToken.UI_BG.color(),
                    borderRadius = 100.dp,
                    blurRadius = 10.dp,
                    offsetY = 8.dp,
                    spreadHeight = 2.dp,
                    spreadWidth = 5.dp
                ),
                icon = OImageRes.Plus,
                iconSize = 32.dp,
                iconColor = AppColors.White,
                backgroundColor = ColorToken.UI_PRIMARY,
                size = 64.dp
            ) {

            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(64.dp)
                    .advanceShadow(
                        color = ColorToken.UI_PRIMARY.color(),
                        blurRadius = 32.dp,
                        spread = 0.01f,
                        offsetY = 20.dp
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
                            .padding(horizontal = 20.dp)
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

                    if (bottomNavigation.route == OmokList) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = OmokList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<OmokList> {
                OmokListScreen()
            }
            composable<MyPage> {
                MyPageScreen()
            }
        }
    }
}