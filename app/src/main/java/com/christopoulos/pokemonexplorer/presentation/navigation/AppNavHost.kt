package com.christopoulos.pokemonexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopoulos.pokemonexplorer.presentation.ui.splash.SplashScreen
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
    }
}

private fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable(Destination.Splash.route) {
        SplashScreen(
            onTimeout = {
                navController.navigateToTypeSelection()
            }
        )
    }
}

private fun NavGraphBuilder.typeSelectionGraph(navController: NavController) {
    composable(Destination.TypeSelection.route) {
        TypeSelectionScreen(
            onTypeSelected = { /* Προς το παρόν δεν κάνουμε navigation. Θα προστεθεί όταν υπάρξει οθόνη TypePokemon. */ }
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