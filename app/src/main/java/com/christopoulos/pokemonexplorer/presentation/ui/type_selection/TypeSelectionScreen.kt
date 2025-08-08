package com.christopoulos.pokemonexplorer.presentation.ui.type_selection

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.christopoulos.pokemonexplorer.domain.model.PokemonType

@Composable
fun TypeSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: TypeSelectionViewModel? = null,
    onTypeSelected: (PokemonType) -> Unit = {}
) {
    val vm: TypeSelectionViewModel = viewModel ?: hiltViewModel()
    val types = vm.types
    val selected = vm.selected

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(types) { type ->
                TypeChip(
                    type = type,
                    selected = selected == type,
                    onClick = {
                        vm.onTypeClick(type)
                        vm.selected?.let { onTypeSelected(it) }
                    },
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Composable
private fun TypeChip(
    type: PokemonType,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = typeColor(type)
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(type.displayName) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = color.copy(alpha = 0.10f),
            selectedContainerColor = color.copy(alpha = 0.25f),
            labelColor = color,
            selectedLabelColor = color
        ),
        modifier = modifier
    )
}

private fun typeColor(type: PokemonType): Color = when (type) {
    PokemonType.NORMAL   -> Color(0xFFA8A77A)
    PokemonType.FIRE     -> Color(0xFFEE8130)
    PokemonType.WATER    -> Color(0xFF6390F0)
    PokemonType.ELECTRIC -> Color(0xFFF7D02C)
    PokemonType.GRASS    -> Color(0xFF7AC74C)
    PokemonType.ICE      -> Color(0xFF96D9D6)
    PokemonType.FIGHTING -> Color(0xFFC22E28)
    PokemonType.POISON   -> Color(0xFFA33EA1)
    PokemonType.GROUND   -> Color(0xFFE2BF65)
    PokemonType.FLYING   -> Color(0xFFA98FF3)
}