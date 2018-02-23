package com.androidhuman.example.simplegithub.di

/**
 * Created by iyeongjun on 2018. 2. 22..
 */
import android.arch.persistence.room.Room
import android.content.Context
import com.androidhuman.example.simplegithub.data.AuthTokenProvider
import com.androidhuman.example.simplegithub.data.SearchHistoryDao
import com.androidhuman.example.simplegithub.data.SimpleGithubDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class LocalDataModule {

    // SharedPreferences

    @Provides
    @Singleton
    fun provideAuthTokenProvider(@Named("appContext") context: Context): AuthTokenProvider
            = AuthTokenProvider(context)

    // Room Database

    @Provides
    @Singleton
    fun provideSearchHistoryDao(db: SimpleGithubDatabase): SearchHistoryDao
            = db.searchHistoryDao()

    @Provides
    @Singleton
    fun provideDatabase(@Named("appContext") context: Context): SimpleGithubDatabase
            = Room.databaseBuilder(context,
            SimpleGithubDatabase::class.java, "simple_github.db")
            .build()
}