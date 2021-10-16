package com.github.brunotorrao.furious.adapters

import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import com.github.brunotorrao.furious.fixtures.simpleMovieDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieDetailsAdapterTest {

    @Test
    fun `given external movie details when converting to domain then should map properties`() {

        val movieDetails = MovieDetailsAdapter.toMovieDetails(1L, simpleExternalMovieDetails())

        assertEquals(simpleMovieDetails(), movieDetails)
    }
}
