package ru.mirea.medlib.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mirea.medlib.network.KinopoiskService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideKinopoiskService(retrofit: Retrofit): KinopoiskService =
        retrofit.create(KinopoiskService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("Temp url")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}