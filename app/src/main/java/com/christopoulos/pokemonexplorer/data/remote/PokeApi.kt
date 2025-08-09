package com.christopoulos.pokemonexplorer.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

// more endpoints in progress..
interface PokeApi {
    // Get all pokemons from one type
    @GET("type/{type}")
    suspend fun getPokemonByType(
        @Path("type") type: String
    ): TypeResponse

    // Get details from Pokémon by Name
    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDetailsResponse


}