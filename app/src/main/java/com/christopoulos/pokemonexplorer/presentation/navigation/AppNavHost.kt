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
import com.christopoulos.pokemonexplorer.presentation.ui.pokemon_details.PokemonDetailsScreen

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
        pokemonSearchGraph(navController)
        detailsPokemonGraph(navController)
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

private fun NavGraphBuilder.pokemonSearchGraph(navController: NavController) {
    composable(
        route = Destination.TypePokemon.route,
        arguments = listOf(navArgument("typeName") { type = NavType.StringType })
    ) { backStackEntry ->
        val typeName = backStackEntry.arguments?.getString("typeName") ?: return@composable
        TypePokemonScreen(
            typeName = typeName,
            onBack = { navController.popBackStack() },
            onPokemonClick = { pokemon ->
                navController.navigate(Destination.detailsRoute(pokemon.name))
            }
        )
    }
}

private fun NavGraphBuilder.detailsPokemonGraph(navController: NavController) {
    composable(
        route = Destination.Details.route,
        arguments = listOf(navArgument("name") { type = NavType.StringType })
    ) {
        PokemonDetailsScreen(
            onBack = { navController.popBackStack() }
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