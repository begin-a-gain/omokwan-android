package com.begin_a_gain.omokwang.navigation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.begin_a_gain.feature.main.MyPageScreen
import com.begin_a_gain.feature.main.OmokListScreen
import com.begin_a_gain.library.design.component.image.OImage
import com.begin_a_gain.omokwang.navigation.MyPage
import com.begin_a_gain.omokwang.navigation.OmokList

@Composable
fun MainGraph() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination
                bottomNavigationRoutes.forEach { bottomNavigation ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(bottomNavigation.route::class) } == true,
                        icon = { OImage(bottomNavigation.icon) },
                        onClick = {
                            navController.navigate(bottomNavigation.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
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