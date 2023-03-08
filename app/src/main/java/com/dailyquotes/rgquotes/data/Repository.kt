package com.dailyquotes.rgquotes.data

import kotlinx.coroutines.flow.Flow

// Repo interface to enable easy testing by creating mock repo
interface Repository {
    suspend fun getQuote(): Flow<Status<QuoteResponse>>
}