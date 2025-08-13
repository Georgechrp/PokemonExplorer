package com.christopoulos.pokemonexplorer.data.repository

import com.christopoulos.pokemonexplorer.data.remote.PokeApi
import com.christopoulos.pokemonexplorer.domain.model.Pokemon

class PokemonRepositoryImpl(
    private val api: PokeApi
) : PokemonRepository {

    override suspend fun getPokemonListByType(type: String, offset: Int, limit: Int): List<Pokemon> {
        val normalizedType = type.trim().lowercase()
        val response = api.getPokemonByType(normalizedType)

        val allPokemonNames = response.pokemon.map { it.pokemon.name }
        val pagedNames = allPokemonNames.drop(offset).take(limit)

        return pagedNames.map { name ->
            val normalizedName = name.trim().lowercase()
            val details = api.getPokemonDetails(normalizedName)
            Pokemon(
                name = details.name,
                imageUrl = details.sprites.front_default ?: "", // fallback αντί για crash
                hp = details.stats.firstOrNull { it.stat.name == "hp" }?.base_stat ?: 0,
                attack = details.stats.firstOrNull { it.stat.name == "attack" }?.base_stat ?: 0,
                defense = details.stats.firstOrNull { it.stat.name == "defense" }?.base_stat ?: 0
            )
        }
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val normalizedName = name.trim().lowercase()
        val details = api.getPokemonDetails(normalizedName)
        return Pokemon(
            name = details.name,
            imageUrl = details.sprites.front_default ?: "",
            hp = details.stats.firstOrNull { it.stat.name == "hp" }?.base_stat ?: 0,
            attack = details.stats.firstOrNull { it.stat.name == "attack" }?.base_stat ?: 0,
            defense = details.stats.firstOrNull { it.stat.name == "defense" }?.base_stat ?: 0
        )
    }
}