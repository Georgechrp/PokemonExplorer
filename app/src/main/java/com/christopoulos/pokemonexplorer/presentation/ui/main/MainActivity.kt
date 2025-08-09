package com.christopoulos.pokemonexplorer.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.christopoulos.pokemonexplorer.presentation.navigation.AppNavHost
import com.christopoulos.pokemonexplorer.ui.theme.PokemonExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonExplorerTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}