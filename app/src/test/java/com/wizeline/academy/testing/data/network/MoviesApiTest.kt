package com.wizeline.academy.testing.data.network

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.test_utils.TestData.ResFilenames
import com.wizeline.academy.testing.test_utils.assertThrows
import com.wizeline.academy.testing.test_utils.data.LocalTestData
import com.wizeline.academy.testing.test_utils.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class MoviesApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: MoviesApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPopularMoviesCorrectRequestWhenFetchingMovies() = runBlocking {
        mockWebServer.enqueueResponse(ResFilenames.POPULAR_MOVIES_200, 200)
        val expectedRequestPath = "/movie/popular"

        api.getPopularMovies()
        val requestPath = mockWebServer.takeRequest().path

        assertThat(requestPath).isEqualTo(expectedRequestPath)
    }

    @Test
    fun getPopularMoviesShouldFetchMoviesCorrectlyGiven200Response() = runTest {
        mockWebServer.enqueueResponse(ResFilenames.POPULAR_MOVIES_200, 200)
        val expectedFirst = LocalTestData.movie634649

        val result = api.getPopularMovies()

        assertThat(result.results?.first()).isEqualTo(expectedFirst)
    }

    @Test
    fun getPopularMoviesShouldReturnResultFailureGiven401Response() = runTest {
        mockWebServer.enqueueResponse(ResFilenames.ERROR_RESPONSE_401, 401)
        assertThrows<HttpException> { api.getPopularMovies() }
    }

    @Test
    fun getMovieCorrectRequestWhenFetchingMovie() {
        mockWebServer.enqueueResponse(ResFilenames.MOVIE_DETAILS_675353_200, 200)
        val movieId = LocalTestData.movieDetails675353.id.toString()
        val expectedRequestPath = "/movie/$movieId"

        api.getMovie(movieId).blockingGet()
        val requestPath = mockWebServer.takeRequest().path

        assertThat(requestPath).isEqualTo(expectedRequestPath)
    }

    @Test
    fun getMovieShouldFetchMovieCorrectlyGiven200Response() {
        mockWebServer.enqueueResponse(ResFilenames.MOVIE_DETAILS_675353_200, 200)
        val expected = LocalTestData.movieDetails675353

        val result = api.getMovie(movieId = expected.id.toString())

        assertThat(result.blockingGet()).isEqualTo(expected)
    }

    @Test
    fun getMovieShouldReturnResultFailureGiven401Response() {
        mockWebServer.enqueueResponse(ResFilenames.ERROR_RESPONSE_401, 401)
        val result = api.getMovie("movieId")
        assertThrows<HttpException> { result.blockingGet() }
    }

}
