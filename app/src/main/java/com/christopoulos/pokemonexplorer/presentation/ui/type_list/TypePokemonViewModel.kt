package com.christopoulos.pokemonexplorer.presentation.ui.type_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopoulos.pokemonexplorer.data.common.toAppError
import com.christopoulos.pokemonexplorer.data.repository.PokemonRepository
import com.christopoulos.pokemonexplorer.domain.common.AppErrorType
import com.christopoulos.pokemonexplorer.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TypeListUiState(
    val isLoading: Boolean = false,
    val error: AppErrorType? = null,
    val items: List<Pokemon> = emptyList(),
    val offset: Int = 0,
    val canLoadMore: Boolean = true,
    val searchQuery: String = ""
) {
    val filteredItems: List<Pokemon>
        get() = if (searchQuery.isBlank()) items
        else items.filter { it.name.contains(searchQuery.trim(), ignoreCase = true) }
}

@HiltViewModel
class TypePokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TypeListUiState())
    val uiState: StateFlow<TypeListUiState> = _uiState

    private var currentType: String? = null
    private var pageSize = 10
    private var loadingJob: Job? = null

    fun init(typeName: String) {
        val normalized = typeName.trim().lowercase()
        if (currentType == normalized && _uiState.value.items.isNotEmpty()) return
        currentType = normalized
        _uiState.value = TypeListUiState(isLoading = true)
        loadNextPage(reset = true)
    }

    fun onSearchChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun loadNextPage(reset: Boolean = false) {
        val type = currentType ?: return
        if (loadingJob?.isActive == true) return

        loadingJob = viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }

                val offset = if (reset) 0 else _uiState.value.offset
                val newItems: List<Pokemon> = repository.getPokemonListByType(
                    type = type,
                    offset = offset,
                    limit = pageSize
                )

                val merged = if (reset) newItems else _uiState.value.items + newItems
                val canLoadMore = newItems.size == pageSize

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = merged,
                        offset = offset + newItems.size,
                        canLoadMore = canLoadMore
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.toAppError()) }
            }
        }
    }

    fun retry() {
        loadNextPage(reset = _uiState.value.items.isEmpty())
    }
}