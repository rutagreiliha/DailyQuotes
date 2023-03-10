package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.room.Quote
import com.dailyquotes.rgquotes.data.room.QuoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Implemented repository that is getting the API injected via hilt, and room
class ImplementedRepository @Inject constructor(
    private val api: QuoteAPI,
    private val room: QuoteDao
) : Repository {
    // Gets 10 new quotes from api
    override suspend fun getNewQuotes(): List<Quote> {
        return api.getQuote()
    }
    // Gets the first quote from room
    override suspend fun getRandomQuoteFromDatabase(): Quote{
        return room.getRandomQuoteFromDatabase()
    }
    // Gets a specific quote from room
    override suspend fun getSpecificQuoteFromDatabase(quote:String?): Quote {
        return room.getSpecificQuoteFromDatabase(quote)
    }
    // Repopulates room with fresh API quotes
    override suspend fun repopulateDB(quotes: List<Quote>) {
        return room.repopulateDB(quotes)
    }
    // Deletes a specific quote from database
    override suspend fun deleteQuoteFromDatabase(quote:String?){
        room.deleteQuoteFromDatabase(quote)
    }
    // Checks if room needs to be repopulated
    override suspend fun roomIsEmpty(): Boolean {
        return room.roomIsEmpty() == 0
    }
    // Function that dermines what actions to take in order to get a quote
    override suspend fun displayQuote(quote: String?): Flow<Status<Quote>> = flow {
        emit(Status.Loading())
        try {
            // If room is not empty
            if (!roomIsEmpty()) {
                // Non specific id
                if (quote == null) {
                    val randomquote = getRandomQuoteFromDatabase()
                    emit(Status.Success(randomquote))
                } else {
                    val specificquote = getSpecificQuoteFromDatabase(quote)
                    emit(Status.Success(specificquote))
                }
            }
            // If room is empty
            else if (roomIsEmpty()) {
                val newquotes = getNewQuotes()
                repopulateDB(newquotes)
                val randomq = getRandomQuoteFromDatabase()
                emit(Status.Success(randomq))
            }
        } catch (e: Exception) {
            emit(Status.Error("There was an error!"))
        }
    }
}