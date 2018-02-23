package com.androidhuman.example.simplegithub.di

import com.androidhuman.example.simplegithub.api.AuthInterceptor
import com.androidhuman.example.simplegithub.api.AuthInterceptor2
import com.androidhuman.example.simplegithub.data.AuthTokenProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by iyeongjun on 2018. 2. 22..
 */

@Module
class NetworkModule{
    @Provides
    @Named("unauthorized")
    @Singleton
    fun provideUnauthorizedOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Named("authorized")
    @Singleton
    fun provideAuthorizedOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            authInterceptor: AuthInterceptor2): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor
            = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideAuthInterceptor(provider: AuthTokenProvider): AuthInterceptor2 {
        val token = provider.token ?: throw IllegalStateException("authToken cannot be null")
        return AuthInterceptor2(token)
    }
}