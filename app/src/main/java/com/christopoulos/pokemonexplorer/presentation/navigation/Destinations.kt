package com.christopoulos.pokemonexplorer.presentation.navigation

/* Here we define the navigation destinations for the app.
 * Each destination corresponds to a screen in the app.
 * The route is used to navigate between screens.
 * we use object because we want to create a singleton instance for each destination.
*/
sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object TypeSelection : Destination("typeSelection")
}