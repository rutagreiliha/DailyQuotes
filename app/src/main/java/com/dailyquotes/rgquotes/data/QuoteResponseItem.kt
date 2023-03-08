package com.dailyquotes.rgquotes.data

// Data class holding each quote
data class QuoteResponseItem(
    val author: String? = null,
    val category: String? = null,
    val quote: String? = null
)