package com.kjjeon.moviepresenter.domain.movie

import com.kjjeon.moviepresenter.data.movie.MovieRepository
import com.kjjeon.moviepresenter.domain.model.Card

class GetCardUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String) = movieRepository.
        get(query)
        .map{ it.items }
        .map {
            val list = ArrayList<Card>()
            it.asIterable().forEach { item ->
                list.add(Card(item.title, item.image, item.userRating))
            }
            list.toList()
        }
//        .onErrorReturn { arrayOf(Card("","", 0.0)).toList() }
}
