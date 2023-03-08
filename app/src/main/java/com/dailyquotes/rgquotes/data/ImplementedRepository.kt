package com.dailyquotes.rgquotes.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImplementedRepository @Inject constructor(private val api: QuoteAPI) : Repository{
    override suspend fun getQuote(): Flow<Status<QuoteResponse>> = flow {
        emit(Status.Loading())
        try {
            val response = api.getQuote()
            emit(Status.Success(response))
        }catch (e: Exception){emit(Status.Error("There was an error!"))}

    }

}