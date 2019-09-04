package com.kjjeon.moviepresenter.presenter.home

import android.util.Log
import com.kjjeon.moviepresenter.domain.movie.GetCardUseCase
import com.kjjeon.moviepresenter.presenter.base.BaseViewModel

class MainViewModel(
    getCardUseCase: GetCardUseCase
) : BaseViewModel() {
    init {
        getCardUseCase("타짜").subscribe { t1, _ -> Log.d("movie", "$t1")  }.drop()
    }
}