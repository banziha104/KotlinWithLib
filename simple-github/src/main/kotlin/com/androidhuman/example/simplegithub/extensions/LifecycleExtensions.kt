package com.androidhuman.example.simplegithub.extensions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver

/**
 * Created by iyeongjun on 2018. 2. 18..
 */

operator fun Lifecycle.plusAssign(observer: LifecycleObserver) = this.addObserver(observer)