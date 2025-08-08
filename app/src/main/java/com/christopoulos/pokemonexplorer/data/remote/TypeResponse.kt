package com.christopoulos.pokemonexplorer.data.remote

data class TypeResponse(
    val pokemon: List<PokemonSlot>
)

data class PokemonSlot(
    val pokemon: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)