package com.christopoulos.pokemonexplorer.data.repository

import com.christopoulos.pokemonexplorer.data.remote.PokeApi
import com.christopoulos.pokemonexplorer.domain.model.Pokemon

class PokemonRepositoryImpl(
    private val api: PokeApi
) : PokemonRepository {

    override suspend fun getPokemonListByType(type: String, offset: Int, limit: Int): List<Pokemon> {
        // API call
        val normalizedType = type.trim().lowercase()
        val response = api.getPokemonByType(normalizedType)

        // get Pokémon names
        val allPokemonNames = response.pokemon.map { it.pokemon.name }

        // pagination (offset, limit)
        val pagedNames = allPokemonNames.drop(offset).take(limit)

        //for every name, get details
        return pagedNames.map { name ->
            val normalizedName = name.trim().lowercase()
            val details = api.getPokemonDetails(name)
            // Mapping σε domain model
            Pokemon(
                name = details.name,
                imageUrl = details.sprites.front_default,
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
            imageUrl = details.sprites.front_default,
            hp = details.stats.firstOrNull { it.stat.name == "hp" }?.base_stat ?: 0,
            attack = details.stats.firstOrNull { it.stat.name == "attack" }?.base_stat ?: 0,
            defense = details.stats.firstOrNull { it.stat.name == "defense" }?.base_stat ?: 0
        )
    }
}