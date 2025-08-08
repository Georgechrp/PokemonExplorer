package com.christopoulos.pokemonexplorer.data.repository
import com.christopoulos.pokemonexplorer.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonListByType(type: String, offset: Int, limit: Int): List<Pokemon>
    suspend fun getPokemonDetails(name: String): Pokemon
}