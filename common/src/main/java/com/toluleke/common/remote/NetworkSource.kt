package com.toluleke.common.remote

import com.toluleke.common.response.CountryDetails
import javax.inject.Inject

class NetworkSource @Inject constructor(
    private val apiService: APIService
) : RemoteDataSource {
    override suspend fun fetchRemoteData() = apiService.fetchCountries()
}

interface RemoteDataSource {
    suspend fun fetchRemoteData(): List<CountryDetails>
}