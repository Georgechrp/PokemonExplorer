package com.christopoulos.pokemonexplorer.presentation.navigation


import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopoulos.pokemonexplorer.presentation.ui.splash.SplashScreen
import com.christopoulos.pokemonexplorer.presentation.ui.type_selection.TypeSelectionScreen

/* defines the navigation graph for the app, including the splash screen and type selection screen.
*  It uses the NavHostController to manage navigation between different screens.
*/
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
        val context = LocalContext.current
        TypeSelectionScreen(
            onTypeSelected = { type ->
                Toast.makeText(context, "Selected: ${type.displayName}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

private fun NavController.navigateToTypeSelection() {
    navigate(Destination.TypeSelection.route) {
        popUpTo(Destination.Splash.route) { inclusive = true } //clear back stack
        launchSingleTop = true
        restoreState = true
    }
}