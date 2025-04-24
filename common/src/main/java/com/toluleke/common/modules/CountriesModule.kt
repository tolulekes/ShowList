package com.toluleke.common.modules

import com.toluleke.common.remote.APIService
import com.toluleke.common.remote.CountriesRepository
import com.toluleke.common.remote.CountriesRepositoryImpl
import com.toluleke.common.remote.NetworkSource
import com.toluleke.common.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CountriesModule {

    @Provides
    fun provideRemoteDataSource(apiService: APIService): RemoteDataSource = NetworkSource(apiService)



    @Provides
    fun provideCountriesRepository(
        remoteDataSource: RemoteDataSource
    ): CountriesRepository {
        return CountriesRepositoryImpl(remoteDataSource)
    }
}
