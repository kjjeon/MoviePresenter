package com.kjjeon.moviepresenter.domain.movie

import com.kjjeon.moviepresenter.data.movie.MovieRepository
import com.kjjeon.moviepresenter.domain.model.Card
import io.reactivex.Maybe

class GetCardUseCase(
    private val movieRepository: MovieRepository
) {
    private lateinit var keyword: String
    private var list = ArrayList<Card>()

    fun newQuery(keyword: String): Maybe<List<Card>> =
        movieRepository.get(keyword, 1)
            .toMaybe()
            .map { it.items }
            .map {
                list = ArrayList()
                it.asIterable().forEach { item ->
                    list.add(Card(item.title, item.image, item.userRating))
                }
                list.toList()
            }.onErrorComplete()
            .also { this.keyword = keyword }

    fun queryNext(page: Int): Maybe<List<Card>> =
        movieRepository.get(keyword, page * 10)
            .filter { it.total > page * 10 }
            .map { it.items }
            .map {
                it.asIterable().forEach { item ->
                    list.add(Card(item.title, item.image, item.userRating))
                }
                list.toList()
            }.onErrorComplete()
}
