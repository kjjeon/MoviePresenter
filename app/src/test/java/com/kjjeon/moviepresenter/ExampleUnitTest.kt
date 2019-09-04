package com.kjjeon.moviepresenter

import com.kjjeon.moviepresenter.di.netModule
import com.kjjeon.moviepresenter.di.presenterModule
import com.kjjeon.moviepresenter.di.repositoryModule
import com.kjjeon.moviepresenter.di.useCaseModule
import com.kjjeon.moviepresenter.domain.movie.GetCardUseCase
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Before
    fun before() {
        startKoin {
            modules(
                listOf(
                    netModule,
                    presenterModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `movie 정보를 받아와서 출력 했을 때 성공`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "타짜" 검색어를 입력 후 클릭 하였을때
        val keyword = "타짜"

        //then
        getCardUseCase(keyword)
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertValue {
                it.last().title == "<b>타짜</b>"
            }
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun `movie 정보를 받아와서 출력 했을 때 실패하는 경우`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "" 검색어를 입력 후 클릭 하였을때
        val keyword = ""

        //then
        getCardUseCase(keyword)
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertError { error ->
                error is HttpException && error.code() == 400 // Bad Request
            }
            .dispose()
    }

}
