package com.begin_a_gain.omokwang.navigation

import androidx.navigation.NavHostController

fun NavHostController.cleanUpTo(route: Any) {
    this.navigate(route) {
        popUpTo(route) { inclusive = true }
    }
}