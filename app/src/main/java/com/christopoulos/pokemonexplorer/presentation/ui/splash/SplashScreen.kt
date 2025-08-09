package com.christopoulos.pokemonexplorer.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.christopoulos.pokemonexplorer.R
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border

/* Here is the SplashScreen with  3 arguments:
*  - onTimeout: Callback to be invoked after the delay(navigateToTypeSelection)
*  - delayMillis: Duration to wait before invoking onTimeout (default is 1200L)
*  - appIconResId: Resource ID of the app icon to display (default is R.drawable.pokemonicon)
*   onTimeout() is called after the specified delay, without touching UI
*/
@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    delayMillis: Long = 1200L,
    appIconResId: Int = R.drawable.pokemonicon
) {
    LaunchedEffect(Unit) {
        delay(delayMillis)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = appIconResId),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Pokémon Explorer",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}