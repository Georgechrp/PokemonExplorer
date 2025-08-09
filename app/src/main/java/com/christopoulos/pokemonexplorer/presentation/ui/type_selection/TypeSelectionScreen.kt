package com.christopoulos.pokemonexplorer.presentation.ui.type_selection

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.christopoulos.pokemonexplorer.R
import com.christopoulos.pokemonexplorer.domain.model.PokemonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: TypeSelectionViewModel? = null,
    onTypeSelected: (PokemonType) -> Unit = {}
) {
    val vm: TypeSelectionViewModel = viewModel ?: hiltViewModel()
    val types = vm.types
    val selected = vm.selected

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filtered = remember(types, searchQuery) {
        val q = searchQuery.text.trim()
        if (q.isEmpty()) types
        else types.filter { it.displayName.contains(q, ignoreCase = true) }
    }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(bgGradient),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Pokémon Explorer",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            )
        },
        bottomBar = {
            AssistBar(
                selected = selected,
                onContinue = { selected?.let(onTypeSelected) }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            SearchField(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            if (filtered.isEmpty()) {
                EmptyState(query = searchQuery.text)
            } else {
                TypeGrid(
                    types = filtered,
                    selected = selected,
                    onClick = {
                        vm.onTypeClick(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Αναζήτηση τύπου…") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        singleLine = true,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
private fun TypeGrid(
    types: List<PokemonType>,
    selected: PokemonType?,
    onClick: (PokemonType) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(types, key = { it.name }) { type ->
            TypeBadgeCard(
                type = type,
                selected = selected == type,
                onClick = { onClick(type) }
            )
        }
    }
}

@Composable
private fun TypeBadgeCard(
    type: PokemonType,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = tween(140, easing = FastOutSlowInEasing),
        label = "pressScale"
    )
    val baseColor = typeColor(type)
    val bgColor by animateColorAsState(
        targetValue = if (selected) baseColor.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(220),
        label = "bgColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) baseColor.copy(alpha = 0.55f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        animationSpec = tween(220),
        label = "borderColor"
    )

    val shape: Shape = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .scale(scale)
            .clip(shape),
        color = bgColor,
        shape = shape,
        tonalElevation = if (selected) 2.dp else 0.dp,
        shadowElevation = if (selected) 2.dp else 0.dp,
        onClick = onClick,
        interactionSource = interaction,
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = Brush.linearGradient(listOf(borderColor, borderColor)))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(baseColor.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = typeIconRes(type)),
                    contentDescription = type.displayName,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = type.displayName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = baseColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                AnimatedContent(
                    targetState = if (selected) "Επιλεγμένο" else "Πάτησε για επιλογή",
                    label = "hint"
                ) { hint ->
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun AssistBar(
    selected: PokemonType?,
    onContinue: () -> Unit
) {
    Surface(tonalElevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected != null) {
                val c = typeColor(selected)
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            selected.displayName,
                            modifier = Modifier.semantics {
                                contentDescription = "Selected type ${selected.displayName}"
                            }
                        )
                    },
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(c)
                        )
                    }
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(containerColor = c)
                ) {
                    Text("Συνέχεια")
                }
            } else {
                Text(
                    "Διάλεξε έναν τύπο για να συνεχίσεις",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                Button(onClick = {}, enabled = false) { Text("Συνέχεια") }
            }
        }
    }
}

@Composable
private fun EmptyState(query: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pokemonicon), // βάλε ένα neutral icon
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(12.dp))
        Text("Κανένας τύπος για “$query”", style = MaterialTheme.typography.titleMedium)
        Text(
            "Δοκίμασε π.χ. Fire, Water, Grass…",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Map icons & colors στα δικά σου assets/types
private fun typeIconRes(type: PokemonType): Int = when (type) {
    PokemonType.NORMAL   -> R.drawable.normal
    PokemonType.FIRE     -> R.drawable.fire
    PokemonType.WATER    -> R.drawable.water
    PokemonType.ELECTRIC -> R.drawable.electric
    PokemonType.GRASS    -> R.drawable.grass
    PokemonType.ICE      -> R.drawable.ice
    PokemonType.FIGHTING -> R.drawable.fighting
    PokemonType.POISON   -> R.drawable.poison
    PokemonType.GROUND   -> R.drawable.ground
    PokemonType.FLYING   -> R.drawable.flying

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