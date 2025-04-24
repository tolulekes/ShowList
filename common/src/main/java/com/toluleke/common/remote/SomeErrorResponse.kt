package com.toluleke.common.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SomeErrorResponse(@Json(name = "message") val message: String="")