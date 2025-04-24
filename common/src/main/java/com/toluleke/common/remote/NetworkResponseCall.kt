package com.toluleke.common.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.nio.charset.Charset

internal class NetworkResponseCall<D : Any, E : Any>(
    private val delegate: Call<D>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<D, E>> {
    override fun clone(): Call<NetworkResponse<D, E>> =
        NetworkResponseCall(delegate.clone(), errorConverter)

    override fun execute(): Response<NetworkResponse<D, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<NetworkResponse<D, E>>) {
        return delegate.enqueue(object : Callback<D> {
            override fun onResponse(call: Call<D>, response: Response<D>) {

                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.ApiError(
                                    "${
                                        error?.let {
                                            errorConverter.convert(it)
                                        }
                                    }", code
                                )
                            )
                        )
                    }
                } else {

                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            val source = error.source()
                            source.request(Long.MAX_VALUE)
                            val buffer = source.buffer()
                            val string = buffer.clone().readString(Charset.forName("UTF-8"))

                            val adapter: JsonAdapter<SomeErrorResponse> = Moshi.Builder().build()
                                .adapter(SomeErrorResponse::class.java)
                            adapter.fromJson(string)

                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            null
                        }
                    }

                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(errorBody.message, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.UnknownError("Unknown Error"))
                        )
                    }
                }

            }

            override fun onFailure(call: Call<D>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.UnknownError(throwable.message)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        return delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}