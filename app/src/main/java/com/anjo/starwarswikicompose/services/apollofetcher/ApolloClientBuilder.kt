package com.anjo.starwarswikicompose.services.apollofetcher

import android.content.Context
import android.os.Looper
import com.anjo.starwarswikicompose.services.interceptor.NetworkConnectionInterceptor
import com.anjo.starwarswikicompose.utils.Constants.APOLLO_BASE_URL
import com.anjo.starwarswikicompose.utils.Constants.APOLLO_DB
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApolloClientBuilder {

    @Singleton
    @Provides
    fun apolloClient(
            @ApplicationContext appContext: Context,
            okHttpClient: OkHttpClient,
    ): ApolloClient {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context = appContext, name = APOLLO_DB)
        val cacheFactory =
            MemoryCacheFactory(maxSizeBytes = 10 * 10 * 1024, expireAfterMillis = 1000 * 60 * 60)
                    .chain(sqlNormalizedCacheFactory)
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }
        return ApolloClient.Builder()
                .dispatcher(Dispatchers.Unconfined)
                .serverUrl(APOLLO_BASE_URL)
                .normalizedCache(cacheFactory)
                .okHttpClient(okHttpClient)
                .fetchPolicy(FetchPolicy.CacheFirst)
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp3Client(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(NetworkConnectionInterceptor(appContext))
                .build()
    }

    @Singleton
    @Provides
    fun provideDataFetcher(
            apolloClient: ApolloClient,
    ): DataFetcher {
        return DataFetcherImpl(
                apolloClient = apolloClient
        )
    }

}
