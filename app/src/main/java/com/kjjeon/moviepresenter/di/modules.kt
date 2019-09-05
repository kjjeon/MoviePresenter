package com.kjjeon.moviepresenter.di

import com.kjjeon.moviepresenter.data.movie.MovieRepository
import com.kjjeon.moviepresenter.data.service.SearchMovieService
import com.kjjeon.moviepresenter.domain.movie.GetCardUseCase
import com.kjjeon.moviepresenter.presenter.custom.EndlessRecyclerViewScrollListener
import com.kjjeon.moviepresenter.presenter.home.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val netModule: Module = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
//            .client(
//                OkHttpClient.Builder().addInterceptor(
//                    HttpLoggingInterceptor().setLevel(
//                        HttpLoggingInterceptor.Level.HEADERS)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(SearchMovieService::class.java) }
}

val repositoryModule = module {
    factory { MovieRepository(get()) }
}

val useCaseModule = module {
    factory { GetCardUseCase(get()) }
}

val presenterModule = module {
    viewModel { MainViewModel(get()) }
}

