package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FlickrClientBuilder {

    @Provides
    @Singleton
    fun retrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        return Retrofit.Builder()
                .baseUrl(FLICKR_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideFlickrApi(retrofitClient: Retrofit): FlickrApi {
        return retrofitClient.create(FlickrApi::class.java)
    }
}