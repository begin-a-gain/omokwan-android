package com.begin_a_gain.omokwang.navigation.match

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.begin_a_gain.feature.match.invite_member.InviteMemberScreen
import com.begin_a_gain.feature.match.match.MatchScreen
import com.begin_a_gain.feature.match.match.MatchSharedViewModel
import com.begin_a_gain.feature.match.match.change_host.ChangeHostScreen
import com.begin_a_gain.feature.match.match.setting.MatchSettingScreen
import com.begin_a_gain.omokwang.navigation.popAndNavigate
import com.begin_a_gain.omokwang.navigation.popBackWithToast
import kotlinx.serialization.Serializable

@Serializable
data class MatchGraph(
    val isInitial: Boolean = false,
    val matchId: Int
)

@Serializable
object Match

@Serializable
data class MatchSetting(
    val matchId: Int
)

@Serializable
object InviteMatch

@Serializable
object ChangeHost

const val ChangeHostToast = "change_host_toast"

fun NavGraphBuilder.matchGraph(
    navController: NavHostController,
    navigateToMain: () -> Unit
) {
    navigation<MatchGraph>(
        startDestination = Match
    ) {
        composable<Match> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<MatchGraph>()
            }
            val args = parentEntry.toRoute<MatchGraph>()
            val sharedViewModel: MatchSharedViewModel = hiltViewModel(parentEntry)
            MatchScreen(
                matchId = args.matchId,
                sharedViewModel = sharedViewModel,
                isInitial = args.isInitial,
                navigateToMain = navigateToMain,
                navigateToSetting = {
                    navController.navigate(MatchSetting(args.matchId))
                }
            )
        }

        composable<MatchSetting> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<MatchGraph>()
            }
            val args = parentEntry.toRoute<MatchGraph>()
            val sharedViewModel: MatchSharedViewModel = hiltViewModel(parentEntry)
            val savedStateHandle = backStackEntry.savedStateHandle
            val toast by savedStateHandle.getStateFlow<String?>(ChangeHostToast, null)
                .collectAsStateWithLifecycle()

            LaunchedEffect(toast) {
                toast?.let { savedStateHandle.remove<String>(ChangeHostToast) }
            }

            MatchSettingScreen(
                matchId = args.matchId,
                toast = toast,
                sharedViewModel = sharedViewModel,
                navigateToMatch = {
                    navController.popAndNavigate(Match)
                },
                navigateToInvite = {
                    navController.navigate(InviteMatch)
                },
                navigateToChangeHost = {
                    navController.navigate(ChangeHost)
                }
            )
        }

        composable<InviteMatch> {
            InviteMemberScreen(
                navigateToSetting = {
                    navController.popBackStack()
                }
            )
        }

        composable<ChangeHost> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<MatchGraph>()
            }
            val args = parentEntry.toRoute<MatchGraph>()
            val sharedViewModel: MatchSharedViewModel = hiltViewModel(parentEntry)
            ChangeHostScreen(
                matchId = args.matchId,
                sharedViewModel = sharedViewModel,
                backToSetting = { toast ->
                    if (toast == null) {
                        navController.popBackStack()
                    } else {
                        navController.popBackWithToast(ChangeHostToast, toast)
                    }
                }
            )
        }
    }
}

