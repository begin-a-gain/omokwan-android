package com.begin_a_gain.omokwang.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.begin_a_gain.feature.match.join_match.JoinMatchScreen
import com.begin_a_gain.feature.sign_in.SignInScreen
import com.begin_a_gain.feature.sign_up.SignUpDoneScreen
import com.begin_a_gain.feature.sign_up.SignUpScreen
import com.begin_a_gain.feature.splash.SplashScreen
import com.begin_a_gain.omokwang.navigation.main.MainGraph
import com.begin_a_gain.omokwang.navigation.match.CreateMatchGraph
import com.begin_a_gain.omokwang.navigation.match.LeaveMatchToast
import com.begin_a_gain.omokwang.navigation.match.MatchGraph
import com.begin_a_gain.omokwang.navigation.match.createMatchGraph
import com.begin_a_gain.omokwang.navigation.match.matchGraph
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

        composable<Main> { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle
            val toast by savedStateHandle.getStateFlow<String?>(LeaveMatchToast, null)
                .collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                toast?.let { savedStateHandle.remove<String>(LeaveMatchToast) }
            }

            MainGraph(
                toast = toast,
                navigateToSignIn = { navController.popAndNavigate(SignIn) },
                navigateToCreateMatch = { navController.navigate(CreateMatchGraph) },
                navigateToJoinMatch = { navController.navigate(JoinMatch) },
                navigateToMatch = { matchId, title ->
                    navController.navigate(MatchGraph(isInitial = false, matchId = matchId, matchTitle = title))
                }
            )
        }

        createMatchGraph(
            navController = navController
        )

        composable<JoinMatch> {
            JoinMatchScreen(
                navigateToMain = { navController.popAndNavigate(Main) },
                navigateToMatch = { matchId, title ->
                    navController.popAndNavigate(MatchGraph(isInitial = false, matchId = matchId, matchTitle = title))
                }
            )
        }

        matchGraph(
            navController = navController,
            navigateToMain = { navController.popAndNavigate(Main) }
        )
    }
}

