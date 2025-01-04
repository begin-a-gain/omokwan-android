package com.begin_a_gain.omokwang.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.begin_a_gain.feature.match.create_match.CreateMatchScreen
import com.begin_a_gain.feature.match.join_match.JoinMatchScreen
import com.begin_a_gain.feature.sign_in.SignInScreen
import com.begin_a_gain.feature.sign_up.SignUpDoneScreen
import com.begin_a_gain.feature.sign_up.SignUpScreen
import com.begin_a_gain.omokwang.navigation.main.MainGraph
import kotlinx.serialization.Serializable

@Serializable object SignIn
@Serializable object SignUp
@Serializable object SignUpDone

@Serializable object Main
@Serializable object MatchList
@Serializable object MyPage

@Serializable object CreateMatch
@Serializable object JoinMatch
@Serializable object Alarm

@Composable
fun OmokwanGraph(
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

        composable<SignUpDone> {
            SignUpDoneScreen(
                navigateToMain = { navController.navigate(Main) }
            )
        }

        composable<Main> {
            MainGraph(
                navigateToCreateMatch = { navController.navigate(CreateMatch) },
                navigateToJoinMatch = { navController.navigate(JoinMatch) }
            )
        }

        composable<CreateMatch> {
            CreateMatchScreen()
        }

        composable<JoinMatch> {
            JoinMatchScreen()
        }
    }
}

