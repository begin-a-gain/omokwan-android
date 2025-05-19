package com.begin_a_gain.omokwang.navigation

import androidx.navigation.NavController

fun NavController.cleanUpTo(route: Any) {
    this.navigate(route) {
        popUpTo(route) { inclusive = true }
    }
}