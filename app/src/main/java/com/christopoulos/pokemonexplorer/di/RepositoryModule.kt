package com.christopoulos.pokemonexplorer.di

import com.christopoulos.pokemonexplorer.data.repository.PokemonRepositoryImpl
import com.christopoulos.pokemonexplorer.data.remote.PokeApi
import com.christopoulos.pokemonexplorer.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//παρέχει υλοποίηση του PokemonRepository (PokemonRepositoryImpl)
// ώστε όπου ζητάμε PokemonRepository να παίρνουμε την υλοποίηση με injected PokeApi.
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApi): PokemonRepository =
        PokemonRepositoryImpl(api)
}