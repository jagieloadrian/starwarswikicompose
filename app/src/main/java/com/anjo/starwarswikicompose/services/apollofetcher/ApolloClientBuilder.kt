package com.anjo.starwarswikicompose.services.apollofetcher

import android.content.Context
import com.anjo.starwarswikicompose.services.interceptor.NetworkConnectionInterceptor
import com.anjo.starwarswikicompose.utils.Constants.APOLLO_BASE_URL
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloClientBuilder {

    @Singleton
    @Provides
    fun apolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
                .serverUrl(APOLLO_BASE_URL)
                .okHttpClient(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp3Client(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(NetworkConnectionInterceptor(appContext))
                .build()
    }

    @Singleton
    @Provides
    fun provideDataFetcher(
            apolloClient: ApolloClient
    ): DataFetcherImpl {
        return DataFetcherImpl(
                apolloClient = apolloClient
        )
    }

}
