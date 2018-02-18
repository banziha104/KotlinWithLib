package com.androidhuman.example.simplegithub.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by iyeongjun on 2018. 2. 18..
 */

operator fun CompositeDisposable.plusAssign(disposable: Disposable){
    this.add(disposable)
}