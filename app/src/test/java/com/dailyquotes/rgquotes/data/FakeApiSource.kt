package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.room.Quote

class FakeApiSource:QuoteAPI {
    override suspend fun getQuote(): List<Quote>{
        return listOf(
            Quote(1,"abc","blah","a"),
            Quote(2,"abcd","blahh","a"),
            Quote(3,"abcde","blahhh","a"),
            Quote(4,"abcdef","blah blah","a"),
            Quote(5,"ab gef c","blah abc","a"),
            Quote(6,"abc fgh","blah lmnop","a"),
            Quote(7,"abcdefghij","blah hijklm","a"),
            Quote(8,"abc lkm","blah 123","a"),
            Quote(9,"ab dghjic","blah nbmkil","a"),
            Quote(10,"abfrg bhjc","bladfeg vhh","a"))
    }
}