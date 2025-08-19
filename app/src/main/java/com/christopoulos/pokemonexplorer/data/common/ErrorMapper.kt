package com.christopoulos.pokemonexplorer.data.common

import com.christopoulos.pokemonexplorer.domain.common.AppErrorType
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toAppError(): AppErrorType = when (this) {
    is UnknownHostException -> AppErrorType.Network
    is SocketTimeoutException -> AppErrorType.Timeout
    is IOException -> AppErrorType.Network
    is HttpException -> when (code()) {
        404 -> AppErrorType.NotFound
        in 500..599 -> AppErrorType.Server
        else -> AppErrorType.Unknown
    }
    else -> AppErrorType.Unknown
}