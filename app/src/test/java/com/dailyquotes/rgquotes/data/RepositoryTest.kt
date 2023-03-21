package com.dailyquotes.rgquotes.data

import app.cash.turbine.test
import com.dailyquotes.rgquotes.data.room.FakeDataSource
import com.dailyquotes.rgquotes.data.room.Quote
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RepositoryTest {
    private lateinit var apiSource: FakeApiSource
    private lateinit var roomSource: FakeDataSource
    private lateinit var roomSourceEmpty: FakeDataSource
    private lateinit var repositoryWhenRoomEmpty: ImplementedRepository
    private lateinit var repository: ImplementedRepository
    private var quote = Quote(11, "gfe", "hjkl", "a")
    private var quoteFromApi = Quote(1, "abc", "blah", "a")
    private var quoteNull = null

    @Before
    fun createRepo() {
        apiSource = FakeApiSource()
        roomSource = FakeDataSource(mutableListOf(quote)) // room contents not empty
        roomSourceEmpty = FakeDataSource(null) // room contents empty
        repository = ImplementedRepository(apiSource, roomSource) //repo when room not empty
        repositoryWhenRoomEmpty =
            ImplementedRepository(apiSource, roomSourceEmpty) //repo when room empty
    }

    // Test that when a specific quote is asked to be retrieved from
    // the room database, that quote is returned
    @Test
    fun test_the_display_quote_function() {

        runTest {
            val expected = Status.Success(quote).data?.quoteId
            repository.displayQuote(quote.quote).test {
                val firstemit = repository.displayQuote(quote.quote).toList()[1].data?.quoteId

                assertEquals(expected, firstemit)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    // Test that when any quote is asked to be retrieved, the first quote from
    // room is retrieved
    @Test
    fun test_the_display_quote_function_if_null() {

        runTest {
            val expected = Status.Success(quote).data?.quoteId
            repository.displayQuote(quoteNull).test {
                val firstemit = repository.displayQuote(quoteNull).toList()[1].data?.quoteId

                assertEquals(expected, firstemit)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    // Test that when room is empty, it returns quote from api source
    @Test
    fun test_the_display_quote_function_if_null_room_empty() {
        runTest {
            val expected = Status.Success(quoteFromApi).data?.quote
            repositoryWhenRoomEmpty.displayQuote(quoteNull).test {
                val firstemit =
                    repositoryWhenRoomEmpty.displayQuote(quoteNull).toList()[1].data?.quote

                assertEquals(expected, firstemit)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test // Should pass when room list isn't empty
    fun test_room_empty_fun_when_room_is_not_empty() {
        runTest {
            val expected = false
            val result = repository.roomIsEmpty()
            assertEquals(expected, result)
        }
    }

    @Test // Should pass when room list is empty
    fun test_room_empty_fun_when_room_is_empty() {
        runTest {
            val expected = true
            val result = repositoryWhenRoomEmpty.roomIsEmpty()
            assertEquals(expected, result)
        }
    }
}