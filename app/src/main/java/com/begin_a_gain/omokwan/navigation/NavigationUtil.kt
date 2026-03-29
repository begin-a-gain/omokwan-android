package com.begin_a_gain.omokwan.navigation

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

fun NavHostController.popBackWithToast(key: String, message: String) {
    this.previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, message)

    this.popBackStack()
}

fun NavHostController.popAndNavigateWithToast(
    route: Any,
    key: String,
    message: String,
) {
    this.popAndNavigate(route)

    this.currentBackStackEntry
        ?.savedStateHandle
        ?.set(key, message)
}