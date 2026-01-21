package com.begin_a_gain.omokwang.navigation.match

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.begin_a_gain.feature.match.invite_member.InviteMemberScreen
import com.begin_a_gain.feature.match.match.MatchScreen
import com.begin_a_gain.feature.match.match.MatchSharedViewModel
import com.begin_a_gain.feature.match.match.change_leader.ChangeLeaderScreen
import com.begin_a_gain.feature.match.match.setting.MatchSettingScreen
import com.begin_a_gain.omokwang.navigation.popAndNavigate
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
object ChangeLeader

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
            MatchSettingScreen(
                matchId = args.matchId,
                sharedViewModel = sharedViewModel,
                navigateToMatch = {
                    navController.popAndNavigate(Match)
                },
                navigateToInvite = {
                    navController.navigate(InviteMatch)
                },
                navigateToChangeLeader = {
                    navController.navigate(ChangeLeader)
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

        composable<ChangeLeader> {
            ChangeLeaderScreen(
                navigateToSetting = {
                    navController.popBackStack()
                }
            )
        }
    }
}

