package com.dailyquotes.rgquotes.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getQuote(): Flow<Status<QuoteResponse>>
}