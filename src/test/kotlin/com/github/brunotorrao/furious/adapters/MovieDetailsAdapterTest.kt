package com.github.brunotorrao.furious.adapters

import arrow.core.toOption
import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import com.github.brunotorrao.furious.fixtures.simpleMovieDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieDetailsAdapterTest {

    @Test
    fun `given external movie details when converting to domain then should map properties`() {

        val movieDetails = simpleExternalMovieDetails()
            .toOption()
            .toMovieDetails(1L)

        assertEquals(simpleMovieDetails(), movieDetails.orNull())
    }

    @Test
    fun `given movie details when converting to response then should return ok with body`() {
        val response = simpleMovieDetails()
            .toOption()
            .toResponse()


        assertEquals(simpleMovieDetails(), response.body)
        assertEquals(200, response.statusCodeValue)

    }

    @Test
    fun `given non existing movie details when converting to response then should return not found`() {

        val response = simpleMovieDetails().toOption().filter { it.id == 10L }
            .toResponse()

        assertEquals("movie not found", response.body)
        assertEquals(404, response.statusCodeValue)
    }
}
