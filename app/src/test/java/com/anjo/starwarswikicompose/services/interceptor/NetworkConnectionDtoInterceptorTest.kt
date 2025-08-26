package com.anjo.starwarswikicompose.services.interceptor

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Response
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


@ExtendWith(MockKExtension::class)
class NetworkConnectionDtoInterceptorTest {

    @MockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var connectivityManager: ConnectivityManager

    @RelaxedMockK
    lateinit var activeNetwork: Network

    @RelaxedMockK
    lateinit var networkCapabilities: NetworkCapabilities

    @RelaxedMockK
    lateinit var chain: Interceptor.Chain

    @RelaxedMockK
    lateinit var response: Response

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 3])
    fun `mock is internet available when intercept then return response`(int: Int) {
        //given
        val mockContext = mockk<Context> {
            every { getSystemService(CONNECTIVITY_SERVICE) } returns connectivityManager
        }
        val networkConnectionInterceptor = NetworkConnectionInterceptor(mockContext)

        every { connectivityManager.activeNetwork } returns activeNetwork
        every { connectivityManager.getNetworkCapabilities(activeNetwork) } returns networkCapabilities
        every { networkCapabilities.hasTransport(int) } returns true
        every { chain.request().newBuilder() } answers { callOriginal() }
        every { chain.proceed(any()) } returns response

        //when
        val actual = networkConnectionInterceptor.intercept(chain)
        //then

        actual shouldBe response
    }

    @Test
    fun `mock is internet not available when intercept then return response`() {
        //given
        val mockContext = mockk<Context> {
            every { getSystemService(CONNECTIVITY_SERVICE) } returns connectivityManager
        }
        val networkConnectionInterceptor = NetworkConnectionInterceptor(mockContext)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { connectivityManager.activeNetwork } returns activeNetwork
        every { connectivityManager.getNetworkCapabilities(activeNetwork) } returns null
        every { chain.request().newBuilder() } answers { callOriginal() }
        every { chain.proceed(any()) } returns response

        //when
        val actual = networkConnectionInterceptor.intercept(chain)

        //then
        actual shouldBe response
        verify(exactly = 1) { Log.e(any(), any()) }
    }
}