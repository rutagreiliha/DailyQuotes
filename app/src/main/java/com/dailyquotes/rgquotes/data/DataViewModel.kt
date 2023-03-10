package com.dailyquotes.rgquotes.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyquotes.rgquotes.data.room.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    // Flow to alert the view of any ui changes
    private val _status = MutableSharedFlow<Status<Quote>?>(replay = 1)
    val status: MutableSharedFlow<Status<Quote>?> = _status

    // Clears the update to avoid confusion/ repeat collects
    suspend fun clearUpdate() {
        _status.emit(null)
    }
    // Func that evokes repo to get quote
    fun getQuote(quote: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.displayQuote(quote).collect { _status.emit(it) }
        }
    }
    //Func that gets new quote from repo
    fun refreshQuote(quote: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteQuoteFromDatabase(quote)
            }
            withContext(Dispatchers.IO) {
                repository.displayQuote(null).collect { _status.emit(it)
                }
            }
        }
    }
}