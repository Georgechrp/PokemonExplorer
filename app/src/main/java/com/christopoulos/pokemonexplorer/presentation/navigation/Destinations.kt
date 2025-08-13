package com.christopoulos.pokemonexplorer.presentation.navigation

object Destination {
    object Splash { const val route = "splash" }
    object TypeSelection { const val route = "type_selection" }
    object TypeList { const val route = "type/{typeName}" }
    fun typeListRoute(typeName: String) = "type/$typeName"

    object Details { const val route = "details/{name}" }
    fun detailsRoute(name: String) = "details/$name"
}