package com.androidhuman.example.simplegithub.api

/**
 * Created by iyeongjun on 2018. 2. 22..
 */

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AuthInterceptor2(private val token: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain)
            : Response = with(chain) {
        val newRequest = request().newBuilder().run {
            addHeader("Authorization", "token " + token)
            build()
        }
        proceed(newRequest)
    }
}