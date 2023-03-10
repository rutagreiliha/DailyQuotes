package com.dailyquotes.rgquotes.data.room

import androidx.room.*

@Dao
interface QuoteDao {
    // Gets any quote from room to display
    @Query("select * from quote_table limit 1")
    suspend fun getRandomQuoteFromDatabase(): Quote
    // Gets a quote based on the id
    @Query("select * from quote_table where quote= :quote")
    suspend fun getSpecificQuoteFromDatabase(quote:String?):Quote
    // Repopulates room with fresh API response
    @Insert()
    suspend fun repopulateDB(quotes: List<Quote>)
    // Deletes quote from database
    @Query("DELETE FROM quote_table WHERE quote = :quote")
    suspend fun deleteQuoteFromDatabase(quote:String?)
    // Checks if database is empty
    @Query("select count(*) from quote_table")
    suspend fun roomIsEmpty(): Int
}