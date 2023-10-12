package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlickrClientBuilder {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun retrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
                .baseUrl(FLICKR_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
    }

    @Provides
    @Singleton
    fun provideFlickrApi(retrofitClient: Retrofit): FlickrApi {
        return retrofitClient.create(FlickrApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFlickrFetcher(retrofitClient: Retrofit) : FlickrApiImpl {
        return FlickrApiImpl(retrofitClient)
    }

}