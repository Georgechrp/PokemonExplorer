package com.christopoulos.pokemonexplorer.data.remote

data class PokemonDetailsResponse(
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>
)

data class Sprites(
    val front_default: String
)

data class Stat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)