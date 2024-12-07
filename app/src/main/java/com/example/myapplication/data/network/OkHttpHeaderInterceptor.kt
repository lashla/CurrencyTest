package com.example.myapplication.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class OkHttpHeaderInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var newRequest: Request

        try {
            newRequest = request.newBuilder()
                .addHeader(HEADER_NAME, API_KEY)
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            return chain.proceed(request)
        }

        return chain.proceed(newRequest)
    }

    companion object {
        const val HEADER_NAME = "apikey"
        //As said in requirements leaving the API key here
        const val API_KEY = "dyPK3JrceYMOz4OaRZKMvbL0tv4uNtly"
    }
}