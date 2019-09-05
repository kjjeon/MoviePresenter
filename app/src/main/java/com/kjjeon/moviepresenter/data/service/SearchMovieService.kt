package com.kjjeon.moviepresenter.data.service

import com.kjjeon.moviepresenter.data.model.Movies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchMovieService {

    @Headers(
        "X-Naver-Client-Id: " + "GED8Or_3rWAv5AXWCOL3",
        "X-Naver-Client-Secret: " + "c9nukY9aSe"
    )
    @GET("v1/search/movie.json")
    fun getSearch(
        @Query("query") query: String,
        @Query("start") start: Int
    ): Single<Movies>
}