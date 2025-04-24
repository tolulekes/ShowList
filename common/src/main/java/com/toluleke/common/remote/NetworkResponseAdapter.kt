package com.toluleke.common.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type


class NetworkResponseAdapter<D: Any, E: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<D, Call<NetworkResponse<D, E>>> {
    override fun responseType(): Type {
        return successType
    }

    override fun adapt(call: Call<D>): Call<NetworkResponse<D, E>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}