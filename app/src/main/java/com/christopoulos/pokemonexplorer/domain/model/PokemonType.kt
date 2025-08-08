package com.christopoulos.pokemonexplorer.domain.model

enum class PokemonType(val displayName: String) {
    NORMAL("Normal"),
    FIRE("Fire"),
    WATER("Water"),
    ELECTRIC("Electric"),
    GRASS("Grass"),
    ICE("Ice"),
    FIGHTING("Fighting"),
    POISON("Poison"),
    GROUND("Ground"),
    FLYING("Flying");

    companion object {
        val default10 = listOf(
            NORMAL, FIRE, WATER, ELECTRIC, GRASS,
            ICE, FIGHTING, POISON, GROUND, FLYING
        )
    }
}