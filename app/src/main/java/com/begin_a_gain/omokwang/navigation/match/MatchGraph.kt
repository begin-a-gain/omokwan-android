package com.begin_a_gain.omokwang.navigation.match

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.begin_a_gain.feature.match.invite_member.InviteMemberScreen
import com.begin_a_gain.feature.match.match.MatchScreen
import com.begin_a_gain.feature.match.match.change_leader.ChangeLeaderScreen
import com.begin_a_gain.feature.match.match.setting.MatchSettingScreen
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
    navController: NavController,
    onNavigateToMain: () -> Unit
) {
    navigation<MatchGraph>(
        startDestination = Match
    ) {
        composable<Match> {
            MatchScreen(
                onNavigateToMain = onNavigateToMain,
                onNavigateToSetting = {
                    navController.navigate(MatchSetting)
                }
            )
        }

        composable<MatchSetting> {
            MatchSettingScreen(
                onNavigateToInvite = {
                    navController.navigate(InviteMatch)
                },
                onNavigateToChangeLeader = {
                    navController.navigate(ChangeLeader)
                }
            )
        }

        composable<InviteMatch> {
            InviteMemberScreen()
        }

        composable<ChangeLeader> {
            ChangeLeaderScreen()
        }
    }
}

