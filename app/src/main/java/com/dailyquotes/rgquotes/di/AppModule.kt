package com.dailyquotes.rgquotes.di

import com.dailyquotes.rgquotes.data.APIaccess.Companion.ACCESS_URL
import com.dailyquotes.rgquotes.data.ImplementedRepository
import com.dailyquotes.rgquotes.data.QuoteAPI
import com.dailyquotes.rgquotes.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuoteAPI(): QuoteAPI = Retrofit.Builder().baseUrl(ACCESS_URL).addConverterFactory(GsonConverterFactory.create()).build().create(QuoteAPI::class.java)


    @Singleton
    @Provides
    fun provideRepository(api: QuoteAPI): Repository = ImplementedRepository(api)

}