package com.christopoulos.pokemonexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //activate Hilt in App, so we can injection to Activities/ViewModels/Repositories.
class PokemonExplorerApp : Application()