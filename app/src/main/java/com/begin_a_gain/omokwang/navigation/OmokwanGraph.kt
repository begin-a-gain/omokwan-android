package com.begin_a_gain.omokwang.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.begin_a_gain.feature.match.create_match.CreateMatchScreen
import com.begin_a_gain.feature.match.create_match.CreateMatchViewModel
import com.begin_a_gain.feature.match.create_match.MatchCategoryScreen
import com.begin_a_gain.feature.match.join_match.JoinMatchScreen
import com.begin_a_gain.feature.match.match.MatchScreen
import com.begin_a_gain.feature.sign_in.SignInScreen
import com.begin_a_gain.feature.sign_up.SignUpDoneScreen
import com.begin_a_gain.feature.sign_up.SignUpScreen
import com.begin_a_gain.feature.splash.SplashScreen
import com.begin_a_gain.omokwang.navigation.main.MainGraph
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object SignUpDone

@Serializable
object Main

@Serializable
object MatchList

@Serializable
object MyPage

@Serializable
data class Match(
    val isInitial: Boolean = false,
    val matchId: Int
)

@Serializable
object MatchCategory

@Serializable
object CreateMatch

@Serializable
object JoinMatch

@Serializable
object Alarm

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OmokwanGraph(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable<Splash> {
            SplashScreen(
                navigateToMain = { navController.popAndNavigate(Main) },
                navigateToSignIn = { navController.popAndNavigate(SignIn) }
            )
        }

        composable<SignIn> {
            SignInScreen(
                navigateToSignUp = { navController.popAndNavigate(SignUp) },
                navigateToMain = { navController.popAndNavigate(Main) }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                navigateToSignUpDone = { navController.popAndNavigate(SignUpDone) },
                popBack = { navController.popBackStack() }
            )
        }

        composable<SignUpDone> {
            SignUpDoneScreen(
                navigateToMain = { navController.popAndNavigate(Main) }
            )
        }

        composable<Main> {
            MainGraph(
                navigateToCreateMatch = { navController.navigate(MatchCategory) },
                navigateToJoinMatch = { navController.navigate(JoinMatch) },
                navigateToMatch = { navController.popAndNavigate(Match) }
            )
        }

        composable<MatchCategory> {
            val backStackEntry = navController.getBackStackEntry(Main)
            val matchViewModel: CreateMatchViewModel = hiltViewModel(backStackEntry)
            MatchCategoryScreen(
                viewModel = matchViewModel,
                navigateToCreateMatch = { navController.popAndNavigate(CreateMatch) },
                navigateToMain = {
                    navController.popAndNavigate(Main)
                }
            )
        }

        composable<CreateMatch> {
            val backStackEntry = navController.getBackStackEntry(Main)
            val matchViewModel: CreateMatchViewModel = hiltViewModel(backStackEntry)
            CreateMatchScreen(
                viewModel = matchViewModel,
                navigateToMain = { navController.popAndNavigate(Main) },
                navigateToMatch = { matchId ->
                    navController.popAndNavigate(Match(isInitial = true, matchId = matchId))
                }
            )
        }

        composable<JoinMatch> {
            JoinMatchScreen(
                navigateToMain = { navController.popAndNavigate(Main) },
                navigateToMatch = { matchId ->
                    navController.popAndNavigate(Match(isInitial = false, matchId = matchId))
                }
            )
        }

        composable<Match> { backstackEntry ->
            val match = backstackEntry.toRoute<Match>()
            MatchScreen(
                isInitial = match.isInitial,
                navigateToMain = { navController.popAndNavigate(Main) }
            )
        }
    }
}

