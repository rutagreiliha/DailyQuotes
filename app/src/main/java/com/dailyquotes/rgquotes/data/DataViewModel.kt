package com.dailyquotes.rgquotes.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    // Flow to alert the view of any ui changes
    private val _status = MutableSharedFlow<Status<QuoteResponse>?>()
    val status: MutableSharedFlow<Status<QuoteResponse>?> = _status

    // Clears the update to avoid confusion/ repeat collects
    suspend fun clearUpdate() {
        _status.emit(null)
    }

    // Func that evokes repo to get quote
    suspend fun getQuote() {
        repository.getQuote().collect { _status.emit(it) }
    }
}