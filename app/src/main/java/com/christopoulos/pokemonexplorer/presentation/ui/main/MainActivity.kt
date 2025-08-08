package com.christopoulos.pokemonexplorer.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.christopoulos.pokemonexplorer.presentation.ui.type_selection.TypeSelectionScreen
import com.christopoulos.pokemonexplorer.ui.theme.PokemonExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonExplorerTheme {
                TypeSelectionScreen(
                    onTypeSelected = { type ->
                        // Προς το παρόν εμφανίζουμε ένα toast
                        Toast.makeText(this, "Selected: ${type.displayName}", Toast.LENGTH_SHORT).show()

                        // αργότερα θα κάνουμε navigation
                    }
                )
            }
        }
    }
}