package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object FlickrClientBuilder {

    @Provides
    fun retrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
                .setStrictness(Strictness.LENIENT)
                .create()
        return Retrofit.Builder()
                .baseUrl(FLICKR_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    fun provideFlickrApi(retrofitClient: Retrofit): FlickrApi {
        return retrofitClient.create(FlickrApi::class.java)
    }
}