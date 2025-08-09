package com.christopoulos.pokemonexplorer.domain.model

import com.christopoulos.pokemonexplorer.R
import com.christopoulos.pokemonexplorer.domain.model.PokemonType


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