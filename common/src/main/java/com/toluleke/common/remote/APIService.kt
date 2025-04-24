package com.toluleke.common.remote

import com.toluleke.common.response.CountryDetails
import retrofit2.http.GET

interface APIService {

    @GET("countries.json")
    suspend fun fetchCountries() : List<CountryDetails>
}