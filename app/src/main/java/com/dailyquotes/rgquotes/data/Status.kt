package com.dailyquotes.rgquotes.data

//Response class
sealed class Status<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Status<T>(data,null)
    class Loading<T> : Status<T>()
    class Error<T>(message: String?) : Status<T>(null, message)
}