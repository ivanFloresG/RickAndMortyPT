package com.aion.rickandmortypt.core.network

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    class Loading<T>(data: T? = null): Result<T>(data)
    class Succes<T>(data: T?): Result<T>(data)
    class Error<T>(message: String?, data : T? = null): Result<T>(data, message)
}