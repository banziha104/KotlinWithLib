package com.androidhuman.example.simplegithub.di.ui

import com.androidhuman.example.simplegithub.api.AuthApi
import com.androidhuman.example.simplegithub.data.AuthTokenProvider
import com.androidhuman.example.simplegithub.ui.signin.SignInViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by iyeongjun on 2018. 2. 22..
 */

@Module
class SignInModule{
    @Provides
    fun provideViewModelFactory(authApi: AuthApi, authTokenProvider: AuthTokenProvider) : SignInViewModelFactory
    = SignInViewModelFactory(authApi, authTokenProvider)
}