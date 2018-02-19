package com.androidhuman.example.simplegithub.extensions

import com.androidhuman.example.simplegithub.rx.AutoClearedDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by iyeongjun on 2018. 2. 18..
 */
operator fun AutoClearedDisposable.plusAssign(disposable: Disposable) = this.add(disposable)
operator fun AutoClearedDisposable.minusAssign(disposable: Disposable) = this.detachSelf()
