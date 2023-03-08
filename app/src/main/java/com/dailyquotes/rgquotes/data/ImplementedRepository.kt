package com.dailyquotes.rgquotes.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Implemented repository that is getting the API injected via hilt
class ImplementedRepository @Inject constructor(private val api: QuoteAPI) : Repository {
    // Implemented func that returns flow of API responses
    override suspend fun getQuote(): Flow<Status<QuoteResponse>> = flow {
        emit(Status.Loading())
        try {
            val response = api.getQuote()
            emit(Status.Success(response))
        } catch (e: Exception) {
            emit(Status.Error("There was an error!"))
        }
    }
}