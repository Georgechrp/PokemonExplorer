# Pokémon Explorer

A sample Android app built with Kotlin, Jetpack Compose, and Clean Architecture, using the PokéAPI to explore Pokémon by type.

## Features

- Select from 10 Pokémon types (Fire, Water, Grass, Electric, Dragon, Psychic, Ghost, Dark, Steel, Fairy)
- Search Pokémon by name within the selected type
- Paginated list of Pokémon (initial 10, with "load more" option)
- Pokémon details: name, image, HP, Attack, Defense

## Architecture

- **Clean Architecture** with clear separation of Data, Domain, and Presentation layers
- **MVVM** (Model-View-ViewModel) pattern
- **Repository Pattern** for data abstraction
- **Dependency Injection** with Hilt
- **Coroutines & Flow** for async and reactive programming
- **Jetpack Compose** for modern UI

## Tech Stack

- Kotlin
- Jetpack Compose
- Hilt (DI)
- Retrofit (network)
- Coroutines & Flow
- PokéAPI (https://pokeapi.co/)

## Project Structure

com.christopoulos.pokemonexplorer
│
├── data # Data sources, API, repository implementations, DTOs
├── di # Dependency injection modules
├── domain # Business logic, use cases, repository interfaces, entities
├── presentation
│ └── ui
│ └── main # MainActivity & main screen UI
└── ui.theme # Theme, colors, typography


## How to Run

1. Clone the repo
2. Open in Android Studio 2025
3. Build & run on an emulator or device (min SDK 24)
4. No API key required (uses public PokéAPI)


## Future Improvements

- Offline cache (Room)
- Localization (multi-language support)
- UI polish & animations
- More detailed Pokémon info

## Presentation

 Check the presentation (includes screenshots, video, .apk file to download)  [here](https://georgioschristopoulos.info/pokemonexplorerpresentation/index.html)

---

*Developed as part of a technical assignment for Desquared S.A.*
