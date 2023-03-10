package com.dailyquotes.rgquotes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class],version = 1)
abstract class Database :RoomDatabase(){
    abstract fun QuoteDao():QuoteDao
}