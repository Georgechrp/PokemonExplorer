package com.christopoulos.pokemonexplorer.presentation.ui.type_selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.christopoulos.pokemonexplorer.domain.model.PokemonType
import com.christopoulos.pokemonexplorer.domain.model.typeIconRes

@Composable
fun TypeSelectionScreen(
    onTypeSelected: (PokemonType) -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(PokemonType.values().first()) }

    Scaffold { inner ->
        TypeSelectionContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            expanded = expanded,
            onDropdownExpand = { expanded = true },
            onDropdownDismiss = { expanded = false },
            selectedType = selectedType,
            onLocalTypeSelected = {
                selectedType = it
                expanded = false
                onTypeSelected(it) // προς το παρόν δεν οδηγεί πουθενά (δεν γίνεται navigate)
            }
        )
    }
}

@Composable
private fun TypeSelectionContent(
    modifier: Modifier = Modifier,
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    expanded: Boolean,
    onDropdownExpand: () -> Unit,
    onDropdownDismiss: () -> Unit,
    selectedType: PokemonType,
    onLocalTypeSelected: (PokemonType) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypeSelectionSearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            expanded = expanded,
            onDropdownExpand = onDropdownExpand,
            onDropdownDismiss = onDropdownDismiss,
            selectedType = selectedType,
            onTypeSelected = onLocalTypeSelected
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Επέλεξε τύπο για να δεις τα Pokémon του τύπου: ${selectedType.displayName}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun TypeSelectionSearchBar(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    expanded: Boolean,
    onDropdownExpand: () -> Unit,
    onDropdownDismiss: () -> Unit,
    selectedType: PokemonType,
    onTypeSelected: (PokemonType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Αναζήτηση Pokémon…") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp)
        )
        Box {
            IconButton(onClick = onDropdownExpand) {
                Image(
                    painter = painterResource(id = typeIconRes(selectedType)),
                    contentDescription = selectedType.displayName,
                    modifier = Modifier.size(28.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDropdownDismiss
            ) {
                PokemonType.values().forEach { t ->
                    DropdownMenuItem(
                        text = { Text(t.displayName) },
                        onClick = { onTypeSelected(t) },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = typeIconRes(t)),
                                contentDescription = t.displayName,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}