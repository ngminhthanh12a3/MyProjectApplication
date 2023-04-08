package com.example.myprojectapplication.services

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("appid", "6eb1c13fd2c0f6f7ce4b884355640bbe").
            addQueryParameter("units", "metric")
            .build()

        val request: Request =chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }

}
