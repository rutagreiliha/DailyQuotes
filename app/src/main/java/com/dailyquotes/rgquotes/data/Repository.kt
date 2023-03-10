package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.room.Quote
import kotlinx.coroutines.flow.Flow

// Repo interface to enable easy testing by creating mock repo
interface Repository {
    suspend fun getNewQuotes(): List<Quote>
    suspend fun getRandomQuoteFromDatabase(): Quote
    suspend fun getSpecificQuoteFromDatabase(quote:String?): Quote
    suspend fun repopulateDB(quotes: List<Quote>)
    suspend fun deleteQuoteFromDatabase(quote:String?)
    suspend fun roomIsEmpty(): Boolean
    suspend fun displayQuote(quote:String?): Flow<Status<Quote>>
}