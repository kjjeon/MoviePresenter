package com.kjjeon.moviepresenter.presenter.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    fun Disposable.drop(): Disposable = apply { compositeDisposable.add(this) }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}