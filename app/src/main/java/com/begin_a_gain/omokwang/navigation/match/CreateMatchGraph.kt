package com.begin_a_gain.omokwang.navigation.match

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.begin_a_gain.feature.match.create_match.CreateMatchScreen
import com.begin_a_gain.feature.match.create_match.CreateMatchViewModel
import com.begin_a_gain.feature.match.create_match.MatchCategoryScreen
import com.begin_a_gain.omokwang.navigation.Main
import kotlinx.serialization.Serializable

@Serializable
object CreateMatchGraph

@Serializable
object MatchCategory

@Serializable
object CreateMatch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.createMatchGraph(
    navController: NavController,
    onNavigateToMatch: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    navigation<CreateMatchGraph>(
        startDestination = MatchCategory
    ) {
        composable<MatchCategory> {
            val backStackEntry = navController.getBackStackEntry(Main)
            val matchViewModel: CreateMatchViewModel = hiltViewModel(backStackEntry)
            MatchCategoryScreen(
                viewModel = matchViewModel,
                navigateToCreateMatch = { navController.navigate(CreateMatch) },
                navigateToMain = {
                    onNavigateToMain()
                }
            )
        }

        composable<CreateMatch> {
            val backStackEntry = navController.getBackStackEntry(Main)
            val matchViewModel: CreateMatchViewModel = hiltViewModel(backStackEntry)
            CreateMatchScreen(
                viewModel = matchViewModel,
                onNavigateToMain = {
                    onNavigateToMain()
                },
                onNavigateToMatch = {

                }
            )
        }
    }
}