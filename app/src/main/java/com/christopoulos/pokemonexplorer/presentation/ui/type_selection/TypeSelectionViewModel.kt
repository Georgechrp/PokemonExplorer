package com.christopoulos.pokemonexplorer.presentation.ui.type_selection


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.christopoulos.pokemonexplorer.domain.model.PokemonType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TypeSelectionViewModel @Inject constructor() : ViewModel() {
    val types: List<PokemonType> = PokemonType.default10

    var selected: PokemonType? by mutableStateOf(null)
        private set

    fun onTypeClick(type: PokemonType) {
        selected = if (selected == type) null else type
    }
}