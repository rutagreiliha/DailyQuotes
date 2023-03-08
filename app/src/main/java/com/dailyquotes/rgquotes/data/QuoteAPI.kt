package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.APIaccess.Companion.API_KEY
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuoteAPI {
    @Headers("X-Api-Key: $API_KEY")
    @GET("v1/quotes?category=happiness")
    suspend fun getQuote(): QuoteResponse
}