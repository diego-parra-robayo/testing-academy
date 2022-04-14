package com.wizeline.academy.testing.di

import com.wizeline.academy.testing.BuildConfig
import com.wizeline.academy.testing.data.network.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @LoggingInterceptor
    fun providesLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @AuthInterceptor
    fun providesAuthInterceptor(): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
                .addQueryParameter("language", Locale.getDefault().language)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(url)
                .build()
            chain.proceed(newRequest)
        }

    @Provides
    fun providesOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @AuthInterceptor authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesMoviesApi(client: OkHttpClient): MoviesApi =
        Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MoviesApi::class.java)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class AuthInterceptor