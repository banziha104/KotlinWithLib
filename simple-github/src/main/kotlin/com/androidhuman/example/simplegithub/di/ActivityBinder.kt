package com.androidhuman.example.simplegithub.di

/**
 * Created by iyeongjun on 2018. 2. 22..
 */

import com.androidhuman.example.simplegithub.di.ui.SignInModule
import com.androidhuman.example.simplegithub.ui.main.MainActivity
import com.androidhuman.example.simplegithub.ui.repo.RepositoryActivity
import com.androidhuman.example.simplegithub.ui.search.SearchActivity
import com.androidhuman.example.simplegithub.ui.signin.SignInActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = arrayOf(SignInModule::class))
    abstract fun bindSignInActivity(): SignInActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun bindRepositoryActivity(): RepositoryActivity
}