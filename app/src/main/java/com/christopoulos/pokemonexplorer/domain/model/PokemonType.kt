package com.christopoulos.pokemonexplorer.domain.model

import com.christopoulos.pokemonexplorer.R

enum class PokemonType(val displayName: String, val apiName: String) {
    NORMAL("Normal", "normal"),
    FIRE("Fire", "fire"),
    WATER("Water", "water"),
    GRASS("Grass", "grass"),
    ELECTRIC("Electric", "electric"),
    ICE("Ice", "ice"),
    FIGHTING("Fighting", "fighting"),
    POISON("Poison", "poison"),
    GROUND("Ground", "ground"),
    FLYING("Flying", "flying");

    companion object {
        val default10 = listOf(NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND, FLYING)
    }
}


fun typeIconRes(type: PokemonType): Int = when (type) {
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