package com.dailyquotes.rgquotes.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Room database table
@Entity(tableName="quote_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quoteId")
    val quoteId:Long,

    @ColumnInfo(name = "author")
    val author:String?=null,

    @ColumnInfo(name = "quote")
    val quote:String?=null,

    @ColumnInfo(name = "category")
    val category:String?=null
)
