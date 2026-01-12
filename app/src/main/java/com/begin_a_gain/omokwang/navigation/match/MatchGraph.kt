package com.begin_a_gain.omokwang.navigation.match

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.begin_a_gain.feature.match.invite_member.InviteMemberScreen
import com.begin_a_gain.feature.match.match.MatchScreen
import com.begin_a_gain.feature.match.match.change_leader.ChangeLeaderScreen
import com.begin_a_gain.feature.match.match.setting.MatchSettingScreen
import com.begin_a_gain.omokwang.navigation.popAndNavigate
import kotlinx.serialization.Serializable

@Serializable
object MatchGraph

@Serializable
object Match

@Serializable
object MatchSetting

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
        composable<Match> {
            MatchScreen(
                isInitial = false,
                navigateToMain = navigateToMain,
                navigateToSetting = {
                    navController.navigate(MatchSetting)
                }
            )
        }

        composable<MatchSetting> {
            MatchSettingScreen(
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

