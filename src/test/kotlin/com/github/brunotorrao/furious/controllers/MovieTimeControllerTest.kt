package com.github.brunotorrao.furious.controllers

import arrow.core.None
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.github.brunotorrao.furious.domain.MovieTimeUpdate
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeGenericException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeNotFoundException
import com.github.brunotorrao.furious.fixtures.movieTimeWithoutId
import com.github.brunotorrao.furious.fixtures.simpleMovieTime
import com.github.brunotorrao.furious.ports.DbMovieTimePort
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class MovieTimeControllerTest {

    lateinit var controller: MovieTimeController

    @MockK
    lateinit var dbMovieTimePort: DbMovieTimePort

    @BeforeEach
    fun init() {
        controller = MovieTimeController(dbMovieTimePort)
    }

    @Test
    fun `given movie id when exists then should return all movie times`() = runBlockingTest {
        coEvery { dbMovieTimePort.findAllByMovieIdOrderByDateDesc(eq(1L)) } returns flowOf(simpleMovieTime())

        val movieTimes = controller.getMovieTimes(1L)

        assertEquals(1, movieTimes.toList().size)
        assertEquals(movieTimes.first(), simpleMovieTime())
    }

    @Test
    fun `given movie id  when no movie times exists then should return empty list`() = runBlockingTest {
        coEvery { dbMovieTimePort.findAllByMovieIdOrderByDateDesc(eq(1L)) } returns emptyFlow()

        val movieTimes = controller.getMovieTimes(1L)

        assertTrue( movieTimes.toList().isEmpty())
    }

    @Test
    fun `given movie time when save is done then should return saved movie time`() = runBlockingTest {
        val movieTime = simpleMovieTime()
        val movieTimeWithoutId = movieTimeWithoutId()

        coEvery { dbMovieTimePort.save(eq(movieTimeWithoutId)) } returns movieTime.right()

        val movieTimes = controller.createMovieTime(1L, movieTime)

        assertEquals(movieTime, movieTimes.body)
        assertEquals(201, movieTimes.statusCodeValue)
    }

    @Test
    fun `given movie time when movie time already exists then should return conflict`() = runBlockingTest {
        val movieTime = simpleMovieTime()
        val movieTimeWithoutId = movieTimeWithoutId()

        coEvery { dbMovieTimePort.save(eq(movieTimeWithoutId)) } returns MovieTimeConflict.left()

        val result = controller.createMovieTime(1L, movieTime)

        assertEquals(409, result.statusCodeValue)
        assertEquals("date for the movie already exists", result.body)
    }

    @Test
    fun `given update when movie time does not exists then should return movie time not found`() = runBlockingTest {
        val movieTimeUpdate = MovieTimeUpdate(18.9.toBigDecimal(), LocalDateTime.of(2021, 10, 16, 20, 40))

        coEvery { dbMovieTimePort.findByIdAndMovieId(eq(1L), eq(1L)) } returns None

        val result = controller.updatePriceAndDateTime(1L, 1L, movieTimeUpdate)

        assertEquals(404, result.statusCodeValue)
        assertEquals("movie time not found", result.body)
    }

    @Test
    fun `given update when movie time exists then should update correctly`() = runBlockingTest {
        val movieTime = simpleMovieTime()
        val movieTimeUpdate = MovieTimeUpdate(18.9.toBigDecimal(), LocalDateTime.of(2021, 10, 16, 20, 40))
        val movieTimeUpdated = movieTime.copy(price = movieTimeUpdate.price, date = movieTimeUpdate.date)


        coEvery { dbMovieTimePort.findByIdAndMovieId(eq(1L), eq(1L)) } returns movieTime.toOption()

        coEvery { dbMovieTimePort.save(eq(movieTimeUpdated)) } returns movieTimeUpdated.right()

        val result = controller.updatePriceAndDateTime(1L, 1L, movieTimeUpdate)

        assertEquals(movieTimeUpdated, result.body)
        assertEquals(200, result.statusCodeValue)
    }

    @Test
    fun `given update when update return error then should return either with error`() = runBlockingTest {
        val movieTime = simpleMovieTime()
        val movieTimeUpdate = MovieTimeUpdate(18.9.toBigDecimal(), LocalDateTime.of(2021, 10, 16, 20, 40))
        val movieTimeUpdated = movieTime.copy(price = movieTimeUpdate.price, date = movieTimeUpdate.date)

        coEvery { dbMovieTimePort.findByIdAndMovieId(eq(1L), eq(1L)) } returns movieTime.toOption()

        coEvery { dbMovieTimePort.save(eq(movieTimeUpdated)) } returns MovieTimeGenericException.left()

        val result = controller.updatePriceAndDateTime(1L, 1L, movieTimeUpdate)

        assertEquals(500, result.statusCodeValue)
        assertEquals("something went wrong creating movie time", result.body)
    }
}
