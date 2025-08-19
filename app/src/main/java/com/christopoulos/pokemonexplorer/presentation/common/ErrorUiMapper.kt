package com.christopoulos.pokemonexplorer.presentation.common

import android.content.Context
import com.christopoulos.pokemonexplorer.R
import com.christopoulos.pokemonexplorer.domain.common.AppErrorType

fun AppErrorType.toUserMessage(ctx: Context): String = when (this) {
    AppErrorType.Network -> ctx.getString(R.string.error_network)
    AppErrorType.Timeout -> ctx.getString(R.string.error_timeout)
    AppErrorType.NotFound -> ctx.getString(R.string.error_not_found)
    AppErrorType.Server -> ctx.getString(R.string.error_server)
    AppErrorType.Unknown -> ctx.getString(R.string.error_unknown)
}