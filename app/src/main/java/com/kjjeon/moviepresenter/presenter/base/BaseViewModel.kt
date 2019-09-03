package com.kjjeon.moviepresenter.presenter.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    open fun stop() {
        compositeDisposable.clear()
    }

    fun Disposable.drop(): Disposable = apply { compositeDisposable.add(this) }
}