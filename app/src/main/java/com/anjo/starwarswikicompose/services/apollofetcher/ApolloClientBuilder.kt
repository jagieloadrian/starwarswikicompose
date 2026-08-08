package com.anjo.starwarswikicompose.services.apollofetcher

import android.content.Context
import com.anjo.starwarswikicompose.services.interceptor.NetworkConnectionInterceptor
import com.anjo.starwarswikicompose.utils.Constants.APOLLO_BASE_URL
import com.anjo.starwarswikicompose.utils.Constants.APOLLO_DB
import com.anjo.starwarswikicompose.apollo.cache.Cache.cache
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.fetchPolicy
import com.apollographql.cache.normalized.memory.MemoryCacheFactory
import com.apollographql.cache.normalized.sql.SqlNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApolloClientBuilder {

    @Provides
    fun apolloClient(
            @ApplicationContext appContext: Context,
            okHttpClient: OkHttpClient,
    ): ApolloClient {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context = appContext, name = APOLLO_DB)
        val cacheFactory =
            MemoryCacheFactory(maxSizeBytes = 10 * 10 * 1024, expireAfterMillis = 1000 * 60 * 60)
                    .chain(sqlNormalizedCacheFactory)
        return ApolloClient.Builder()
                .dispatcher(Dispatchers.Unconfined)
                .serverUrl(APOLLO_BASE_URL)
                .cache(cacheFactory)
                .okHttpClient(okHttpClient)
                .fetchPolicy(FetchPolicy.CacheFirst)
                .build()
    }

    @Provides
    fun provideOkHttp3Client(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            addInterceptor(NetworkConnectionInterceptor(appContext))
        }.build()
    }


    @Provides
    fun provideDataFetcher(
            apolloClient: ApolloClient,
    ): DataFetcher {
        return DataFetcherImpl(
                apolloClient = apolloClient
        )
    }

}
