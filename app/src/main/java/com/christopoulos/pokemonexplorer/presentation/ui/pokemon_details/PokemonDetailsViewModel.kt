package com.christopoulos.pokemonexplorer.presentation.ui.pokemon_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopoulos.pokemonexplorer.data.common.toAppError
import com.christopoulos.pokemonexplorer.data.repository.PokemonRepository
import com.christopoulos.pokemonexplorer.domain.common.AppErrorType
import com.christopoulos.pokemonexplorer.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonDetailsUiState(
    val isLoading: Boolean = true,
    val error: AppErrorType? = null,
    val pokemon: Pokemon? = null
)

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailsUiState())
    val uiState: StateFlow<PokemonDetailsUiState> = _uiState

    private val pokemonName: String? = savedStateHandle["name"]

    init {
        load()
    }

    fun load() {
        val name = pokemonName ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val details = repository.getPokemonDetails(name)
                _uiState.update { it.copy(isLoading = false, pokemon = details) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.toAppError()) }
            }
        }
    }
}