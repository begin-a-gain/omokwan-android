package com.begin_a_gain.omokwan.navigation.match

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
import com.begin_a_gain.omokwan.navigation.Main
import com.begin_a_gain.omokwan.navigation.popAndNavigate
import com.begin_a_gain.omokwan.navigation.popAndNavigateWithToast
import com.begin_a_gain.omokwan.navigation.popBackWithToast
import kotlinx.serialization.Serializable

@Serializable
data class MatchGraph(
    val isInitial: Boolean = false,
    val matchId: Int
)

@Serializable
object Match

@Serializable
object MatchSetting

@Serializable
object InviteMatch

@Serializable
object ChangeHost

const val ChangeHostToast = "change_host_toast"
const val LeaveMatchToast = "leave_match_toast"
const val InviteToast = "invite_toast"

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
                    navController.navigate(MatchSetting)
                },
                navigateToInvite = {
                    navController.navigate(InviteMatch)
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
            val changeHostToast by savedStateHandle.getStateFlow<String?>(ChangeHostToast, null)
                .collectAsStateWithLifecycle()
            val inviteToast by savedStateHandle.getStateFlow<String?>(InviteToast, null)
                .collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                changeHostToast?.let { savedStateHandle.remove<String>(ChangeHostToast) }
                inviteToast?.let { savedStateHandle.remove<String>(InviteToast) }
            }

            MatchSettingScreen(
                matchId = args.matchId,
                toast = when {
                    changeHostToast != null -> changeHostToast
                    inviteToast != null -> inviteToast
                    else -> null
                },
                sharedViewModel = sharedViewModel,
                backToMain = { toast ->
                    if (!toast.isNullOrBlank()) {
                        navController.popAndNavigateWithToast(Main, LeaveMatchToast, toast)
                    }
                },
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

        composable<InviteMatch> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<MatchGraph>()
            }
            val parentArg = parentEntry.toRoute<MatchGraph>()
            InviteMemberScreen(
                matchId = parentArg.matchId,
                navigateToSetting = { toast ->
                    if (toast == null) {
                        navController.popBackStack()
                    } else {
                        navController.popBackWithToast(InviteToast, toast)
                    }
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

