package com.kjjeon.moviepresenter.presenter.home

import androidx.lifecycle.MutableLiveData
import com.kjjeon.moviepresenter.domain.model.Card
import com.kjjeon.moviepresenter.domain.movie.GetCardUseCase
import com.kjjeon.moviepresenter.presenter.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(
    private val getCardUseCase: GetCardUseCase
) : BaseViewModel() {

    var cardListLiveData = MutableLiveData<List<Card>>()

    fun query(keyword: String) {
        getCardUseCase(keyword)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, _ -> cardListLiveData.value = t1  }.drop()
    }

    fun test() {
        getCardUseCase("타짜")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, _ -> cardListLiveData.value = t1  }.drop()
    }
}