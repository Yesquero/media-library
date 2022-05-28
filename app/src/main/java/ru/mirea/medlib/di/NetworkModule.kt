package ru.mirea.medlib.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mirea.medlib.BuildConfig
import ru.mirea.medlib.network.KinopoiskService
import ru.mirea.medlib.utility.MedLibConstants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(loggingInterceptor)
        }
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header(MedLibConstants.NIKOPOISK_API_HEADER_NAME, BuildConfig.KINOPOISK_API_KEY)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideKinopoiskService(retrofit: Retrofit): KinopoiskService =
        retrofit.create(KinopoiskService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.KINOPOISK_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}