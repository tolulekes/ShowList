package com.toluleke.common.remote

import java.io.IOException

sealed class NetworkResponse<out T: Any, out E: Any> {
    data class Success<T: Any>(val data: T): NetworkResponse<T, Nothing>()
    data class ApiError<E: Any>(val message: String, val code: Int): NetworkResponse<Nothing, E>()
    data class NetworkError<E: Any>(val error: IOException): NetworkResponse<Nothing, E>()
    data class UnknownError(val message: String?): NetworkResponse<Nothing, Nothing>()
}