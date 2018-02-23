package com.androidhuman.example.simplegithub.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import com.androidhuman.example.simplegithub.SimpleGithubApp

/**
 * Created by iyeongjun on 2018. 2. 22..
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        LocalDataModule::class,
        ApiModule::class,
        NetworkModule::class,
        AndroidInjectionModule::class,
        ActivityBinder::class))
interface AppComponent : AndroidInjector<SimpleGithubApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}