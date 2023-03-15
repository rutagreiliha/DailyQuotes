package com.dailyquotes.rgquotes.data.room

class FakeDataSource(var quoteslist: MutableList<Quote>? = mutableListOf()):QuoteDao {
    override suspend fun getRandomQuoteFromDatabase(): Quote {
        return quoteslist!![0]

    }

    override suspend fun getSpecificQuoteFromDatabase(quote: String?): Quote {
        return quoteslist?.find { it.quote==quote }!!
    }

    override suspend fun repopulateDB(quotes: List<Quote>) {
        for (quote in quotes){
            quoteslist?.add(quote)
        }
    }

    override suspend fun deleteQuoteFromDatabase(quote: String?) {
        quoteslist?.removeIf { it.quote == quote }
    }

    override suspend fun roomIsEmpty(): Int {
        return if (quoteslist != null){
            quoteslist!!.size
        } else{
            0
        }
    }


}