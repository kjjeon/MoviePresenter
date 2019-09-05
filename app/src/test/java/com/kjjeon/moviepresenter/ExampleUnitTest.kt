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
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValue {
                it.last().title == "<b>타짜</b>" //귀찮아서 안바꾸긴 했는데 이게 마지막 검색 결과가 타짜가 아닐수도 ?? 코딩 당시에는 타짜임
            }
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun `사용자가 검색어를 입력 하지 않고 요청 하였을 때`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        val keyword = ""

        //원래 서버에 요청하면 400에러가 발생 하는데 MayBe로 반환 하여 배출되지 않아야함.
        //then
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
//            .assertError { error ->
//                error is HttpException && error.code() == 400 // Bad Request
//            }
            .assertComplete()
            .dispose()
    }

    @Test
    fun `서버에 movie 요청 후 다음 정보를 요청함 20개의 정보가 읽혀져야함`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "" 검색어를 입력 후 클릭 하였을때
        val keyword = "가"

        //then
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()

        //page의 처음은 0 page 부터임
        getCardUseCase.queryNext(1)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValue {
                it.size == 20
            }
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun `서버에 movie 정보를 추가로 요청 했는데 정보가 없음`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "" 검색어를 입력 후 클릭 하였을때
        val keyword = "타짜"

        //then
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()

        //정보가 없으면 MayBe로 반환되므로 Complete 만 호출됨
        getCardUseCase.queryNext(1)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()

        //정보가 없으면 MayBe로 반환되므로 Complete 만 호출됨
        getCardUseCase.queryNext(2)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()
    }

    @Test
    fun `movie 정보를 같은 page 정보를 계속 요청함`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "" 검색어를 입력 후 클릭 하였을때
        val keyword = "타짜"

        //then
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()

        //정보가 없으면 MayBe로 반환되므로 Complete 만 호출됨
        getCardUseCase.queryNext(1)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()

        //정보가 없으면 MayBe로 반환되므로 Complete 만 호출됨
        getCardUseCase.queryNext(1)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()

        //정보가 없으면 MayBe로 반환되므로 Complete 만 호출됨
        getCardUseCase.queryNext(1)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()
    }

    @Test
    fun `사용자가 최대 허용 범위를 넘어서 요청함`() {
        //given
        val getCardUseCase by inject(GetCardUseCase::class.java)

        //when
        //user가 "" 검색어를 입력 후 클릭 하였을때
        val keyword = "타짜"

        //then
        getCardUseCase.newQuery(keyword)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .dispose()

        //원래 서버에 요청하면 start가 1000 (105 * 10)이 넘기 때문에 400에러가 발생 하는데 MayBe로 반환 하여 배출되지 않아야함.
        getCardUseCase.queryNext(105)
            .test()
            .awaitDone(1, TimeUnit.SECONDS)
            .assertComplete()
            .dispose()
    }
}
