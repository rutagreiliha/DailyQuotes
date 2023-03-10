package com.dailyquotes.rgquotes.di

import android.content.Context
import androidx.room.Room
import com.dailyquotes.rgquotes.data.APIaccess.Companion.ACCESS_URL
import com.dailyquotes.rgquotes.data.ImplementedRepository
import com.dailyquotes.rgquotes.data.QuoteAPI
import com.dailyquotes.rgquotes.data.Repository
import com.dailyquotes.rgquotes.data.room.Database
import com.dailyquotes.rgquotes.data.room.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuoteAPI(): QuoteAPI =
        Retrofit.Builder().baseUrl(ACCESS_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(QuoteAPI::class.java)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "quote_table"
    ).build()

    @Singleton
    @Provides
    fun provideQuoteDao(database:Database): QuoteDao = database.QuoteDao()

    @Singleton
    @Provides
    fun provideRepository(api: QuoteAPI, room:QuoteDao): Repository = ImplementedRepository(api,room)

}