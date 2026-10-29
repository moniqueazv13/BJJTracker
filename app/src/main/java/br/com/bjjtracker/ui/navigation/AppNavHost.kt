package br.com.bjjtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.bjjtracker.ui.Route
import br.com.bjjtracker.ui.home.HomeScreen
import br.com.bjjtracker.ui.home.HomeUiState

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Route = Route.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen(
                state = HomeUiState(),
                onAddTraining = {},
                onRegisterTraining = {},
            )
        }
    }
}
