package com.dailyquotes.rgquotes.data

import com.dailyquotes.rgquotes.data.room.FakeDataSource
import com.dailyquotes.rgquotes.data.room.Quote
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RepositoryTest {
    private lateinit var apiSource: FakeApiSource
    private lateinit var roomSource: FakeDataSource
    private lateinit var repository:ImplementedRepository
    //private var quote = mutableListOf(Quote(11,"gfe","hjkl","a"))
    private var quote = null

    @Before
    public fun createRepo(){
        apiSource= FakeApiSource()
        roomSource= FakeDataSource(quote) // room contents
        repository= ImplementedRepository(apiSource,roomSource)
    }
    @Test
    fun test_the_display_quote_function() {

        runBlocking {
            val expected = listOf(Status.Loading(), Status.Success(Quote(1, "abc", "blah", "a")))[0]
            val firstemit = repository.displayQuote(quote).first()

            assertEquals(expected, firstemit) }
    }

    @Test // Should pass when room list isn't empty
    fun test_room_empty_fun_when_room_is_not_empty() {
        runBlocking {  val expected = false
            val result = repository.roomIsEmpty()
            assertEquals(expected, result) }
    }

    @Test // Should pass when room list is empty
    fun test_room_empty_fun_when_room_is_empty() {
        runBlocking {  val expected = true
            val result = repository.roomIsEmpty()
            assertEquals(expected, result) }
    }
}