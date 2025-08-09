package com.christopoulos.pokemonexplorer.di

import com.christopoulos.pokemonexplorer.data.remote.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule { //create singleton Retrofit by baseUrl & Gson, also PokeApi for injection.

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePokeApi(retrofit: Retrofit): PokeApi =
        retrofit.create(PokeApi::class.java)
}