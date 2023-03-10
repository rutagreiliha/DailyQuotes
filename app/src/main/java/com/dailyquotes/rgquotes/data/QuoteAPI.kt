package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.APIaccess.Companion.API_KEY
import com.dailyquotes.rgquotes.data.room.Quote
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuoteAPI {
    @Headers("X-Api-Key: $API_KEY")
    @GET("v1/quotes?category=happiness&limit=10") // 10 quotes per response
    suspend fun getQuote(): List<Quote>
}