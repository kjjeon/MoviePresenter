package com.kjjeon.moviepresenter.presenter.home

import androidx.lifecycle.MutableLiveData
import com.kjjeon.moviepresenter.domain.model.Card
import com.kjjeon.moviepresenter.domain.movie.GetCardUseCase
import com.kjjeon.moviepresenter.presenter.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(
    private val getCardUseCase: GetCardUseCase
) : BaseViewModel() {
    private lateinit var keyword: String
    var cardListLiveData = MutableLiveData<Pair<Int, List<Card>>>()

    fun newQuery(keyword: String) {
        this.keyword = keyword
        getCardUseCase.newQuery(keyword)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1 -> cardListLiveData.value = 1 to t1  }.drop()
    }

    fun queryNext(page: Int) {
        getCardUseCase.queryNext(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1 -> cardListLiveData.value = page to t1  }.drop()
    }
}