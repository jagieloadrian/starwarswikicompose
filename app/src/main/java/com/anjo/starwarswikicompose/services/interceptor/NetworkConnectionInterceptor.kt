package com.anjo.starwarswikicompose.services.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.anjo.starwarswikicompose.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkConnectionInterceptor(
        val context:Context
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()){
            throw NoConnectivityException("No internet :(")
        }

        val builder:Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    fun isConnected():Boolean{
        val connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}