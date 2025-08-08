package com.christopoulos.pokemonexplorer.domain.model

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int
)