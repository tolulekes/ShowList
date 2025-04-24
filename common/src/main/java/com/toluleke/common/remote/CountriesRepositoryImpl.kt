package com.toluleke.common.remote

import com.toluleke.common.response.CountryDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): CountriesRepository {
    override suspend fun getItems(): Flow<List<CountryDetails>> {
        return flow { emit(remoteDataSource.fetchRemoteData()) }
    }
}

interface CountriesRepository {
    suspend fun getItems(): Flow<List<CountryDetails>>
}
