package com.dailyquotes.rgquotes.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _status = MutableSharedFlow<Status<QuoteResponse>?>()
    val status: MutableSharedFlow<Status<QuoteResponse>?> = _status

    suspend fun clearUpdate() {
        _status.emit(null)
    }

    suspend fun getQuote() {
        repository.getQuote().collect { _status.emit(it) }
    }

}