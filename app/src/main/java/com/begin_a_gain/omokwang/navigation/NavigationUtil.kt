package com.begin_a_gain.omokwang.navigation

import androidx.navigation.NavHostController

fun NavHostController.popAndNavigate(route: Any) {
    val currentRoute = currentDestination?.route
    this.navigate(route) {
        if (currentRoute != null) {
            popUpTo(currentRoute) { inclusive = true }
        }
        launchSingleTop = true
    }
}