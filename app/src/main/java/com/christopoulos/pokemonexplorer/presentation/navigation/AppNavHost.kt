package com.christopoulos.pokemonexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.christopoulos.pokemonexplorer.presentation.ui.splash.SplashScreen
import com.christopoulos.pokemonexplorer.presentation.ui.type_list.TypePokemonScreen
import com.christopoulos.pokemonexplorer.presentation.ui.type_selection.TypeSelectionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Destination.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        splashGraph(navController)
        typeSelectionGraph(navController)
        typeListGraph(navController)
    }
}

private fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable(Destination.Splash.route) {
        SplashScreen(onTimeout = { navController.navigateToTypeSelection() })
    }
}

private fun NavGraphBuilder.typeSelectionGraph(navController: NavController) {
    composable(Destination.TypeSelection.route) {
        TypeSelectionScreen(
            onTypeSelected = { type ->
                navController.navigate(Destination.typeListRoute(type.apiName))
            }
        )
    }
}

private fun NavGraphBuilder.typeListGraph(navController: NavController) {
    composable(
        route = Destination.TypeList.route,
        arguments = listOf(navArgument("typeName") { type = NavType.StringType })
    ) { backStackEntry ->
        val typeName = backStackEntry.arguments?.getString("typeName") ?: return@composable
        TypePokemonScreen(
            typeName = typeName,
            onBack = { navController.popBackStack() },
            onPokemonClick = { /* Θα το χρησιμοποιήσουμε στο επόμενο βήμα για λεπτομέρειες */ }
        )
    }
}

private fun NavController.navigateToTypeSelection() {
    navigate(Destination.TypeSelection.route) {
        popUpTo(Destination.Splash.route) { inclusive = true }
        launchSingleTop = true
        restoreState = true
    }
}