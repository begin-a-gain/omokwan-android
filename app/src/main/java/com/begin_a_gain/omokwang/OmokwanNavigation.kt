package com.begin_a_gain.omokwang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.begin_a_gain.feature.sign_in.SignInScreen
import com.begin_a_gain.feature.sign_up.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable object SignIn
@Serializable object SignUp
@Serializable object SignUpDone

@Serializable object Main
@Serializable object MyPage

@Composable
fun OmokwanNavigation(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable<SignIn> {
            SignInScreen(
                navigateToSignUp = { navController.navigate(SignUp) },
                navigateToMain = { navController.navigate(Main) }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                navigateToSignUpDone = { navController.navigate(SignUpDone) },
                popBack = { navController.popBackStack() }
            )
        }
    }
}