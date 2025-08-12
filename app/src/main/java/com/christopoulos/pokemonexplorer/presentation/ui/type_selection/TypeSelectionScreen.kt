package com.christopoulos.pokemonexplorer.presentation.ui.type_selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.christopoulos.pokemonexplorer.domain.model.PokemonType
import com.christopoulos.pokemonexplorer.domain.model.typeIconRes

@Composable
fun TypeSelectionScreen(
    onTypeSelected: (PokemonType) -> Unit
) {
    Scaffold { inner ->
        TypeSelectionContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            onTypeSelected = onTypeSelected
        )
    }
}

@Composable
private fun TypeSelectionContent(
    modifier: Modifier = Modifier,
    onTypeSelected: (PokemonType) -> Unit
) {
    val types = remember { PokemonType.values().toList() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Επέλεξε τύπο για να δεις τα Pokémon του τύπου",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(types) { type ->
                TypeCard(
                    type = type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
    }
}

@Composable
private fun TypeCard(
    type: PokemonType,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = typeIconRes(type)),
                    contentDescription = type.displayName,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = type.displayName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}