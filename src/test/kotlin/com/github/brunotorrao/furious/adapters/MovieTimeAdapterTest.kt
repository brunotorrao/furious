package com.github.brunotorrao.furious.adapters

import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeGenericException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeMovieNotFounExcetpion
import com.github.brunotorrao.furious.fixtures.simpleMovieTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.URI

class MovieTimeAdapterTest {

    @Test
    fun `given movie time when converting to response then should map to ok with body`() {
        val response = simpleMovieTime().right()
            .toOption()
            .toResponse()


        assertEquals(simpleMovieTime(), response.body)
        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun `given non existing movie time when converting to response then should map to not found`() {
        val response = simpleMovieTime().right()
            .toOption().filter { it.orNull()?.id == 10L }
            .toResponse()

        assertEquals("movie time not found", response.body)
        assertEquals(404, response.statusCodeValue)
    }

    @Test
    fun `given conflicting existing movie time when converting to response then should map to conflict`() {
        val response = MovieTimeConflict.left()
            .toOption()
            .toResponse()

        assertEquals("date for the movie already exists", response.body)
        assertEquals(409, response.statusCodeValue)
    }

    @Test
    fun `given unknown error when creating movie time then should map to internal server error`() {
        val response = MovieTimeGenericException.left()
            .toOption()
            .toResponse()

        assertEquals("something went wrong creating movie time", response.body)
        assertEquals(500, response.statusCodeValue)
    }

    @Test
    fun `given movie not found when creating movie time then should map to not found`() {
        val response = MovieTimeMovieNotFounExcetpion.left()
            .toOption()
            .toResponse()

        assertEquals("movie not found", response.body)
        assertEquals(404, response.statusCodeValue)
    }

    @Test
    fun `given movie time created when converting to response then should map to created with body and location`() {
        val response = simpleMovieTime().right()
            .toCreatedResponse(1L)


        assertEquals(simpleMovieTime(), response.body)
        assertEquals(201, response.statusCodeValue)
        assertEquals(URI.create("/movies/1/times/1"), response.headers.location)
    }
}
