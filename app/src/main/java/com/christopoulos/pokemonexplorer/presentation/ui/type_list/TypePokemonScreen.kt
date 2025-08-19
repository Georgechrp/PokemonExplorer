package com.christopoulos.pokemonexplorer.presentation.ui.type_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.christopoulos.pokemonexplorer.domain.model.Pokemon
import androidx.compose.runtime.collectAsState
import com.christopoulos.pokemonexplorer.R
import com.christopoulos.pokemonexplorer.presentation.common.toUserMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypePokemonScreen(
    typeName: String,
    onBack: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: TypePokemonViewModel = hiltViewModel()
) {
    LaunchedEffect(typeName) {
        viewModel.init(typeName)
    }

    val state by viewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Type: $typeName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {
            var searchText by remember { mutableStateOf(TextFieldValue("")) }

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.onSearchChange(it.text)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search within type…") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            when {
                state.isLoading && state.items.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null && state.items.isEmpty() -> {
                    val errorMessage = state.error?.toUserMessage(ctx) ?: ctx.getString(R.string.error_unknown)
                    ErrorView(message = errorMessage, onRetry = viewModel::retry)
                }

                state.filteredItems.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Pokémon found for your search.")
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = state.filteredItems,
                            key = { it.name } // stable key for smoother list behavior
                        ) { pokemon ->
                            PokemonRow(pokemon = pokemon) { onPokemonClick(pokemon) }
                            Divider()
                        }

                        item {
                            if (state.canLoadMore && state.error == null) {
                                LoadMoreRow(
                                    loading = state.isLoading,
                                    onClick = { viewModel.loadNextPage() }
                                )
                            }
                        }

                        item {
                            if (!state.canLoadMore) {
                                Spacer(Modifier.height(16.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "End of results.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonRow(pokemon: Pokemon, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(text = pokemon.name, style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = onClick) {
            Text("Details")
        }
    }
}

@Composable
private fun LoadMoreRow(loading: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        } else {
            OutlinedButton(onClick = onClick) { Text("Load more") }
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onRetry) { Text("Try again") }
    }
}