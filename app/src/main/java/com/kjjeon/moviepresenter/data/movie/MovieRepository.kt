package com.kjjeon.moviepresenter.data.movie

import com.kjjeon.moviepresenter.data.service.SearchMovieService
import io.reactivex.schedulers.Schedulers

class MovieRepository(
    private val searchMovieService: SearchMovieService
) {
    fun get(query: String,start: Int) = searchMovieService
        .getSearch(query,start)
        .subscribeOn(Schedulers.io())
}