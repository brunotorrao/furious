package com.github.brunotorrao.furious.adapters

import arrow.core.left
import arrow.core.right
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewConflictException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewGenericException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewMovieNotFoundException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewRatingException
import com.github.brunotorrao.furious.fixtures.simpleReview
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReviewAdapterTest {

    @Test
    fun `given review when converting to created response then should map to created with body`() {
        val response = simpleReview().right()
            .toCreatedResponse(1L)


        assertEquals(simpleReview(), response.body)
        assertEquals(201, response.statusCodeValue)
    }

    @Test
    fun `given non existing movie when converting to response then should map to not found`() {
        val response = ReviewMovieNotFoundException.left()
            .toCreatedResponse(1L)

        assertEquals("movie not found", response.body)
        assertEquals(404, response.statusCodeValue)
    }

    @Test
    fun `given invalid review when converting to response then should map to bad request`() {
        val response = ReviewRatingException.left()
            .toCreatedResponse(1L)

        assertEquals("review rating must be between 1 and 5", response.body)
        assertEquals(400, response.statusCodeValue)
    }

    @Test
    fun `given conflicting existing review when converting to response then should map to conflict`() {
        val response = ReviewConflictException.left()
            .toCreatedResponse(1L)

        assertEquals("review for the movie already exists", response.body)
        assertEquals(409, response.statusCodeValue)
    }

    @Test
    fun `given unknown error when creating review then should map to internal server error`() {
        val response = ReviewGenericException.left()
            .toCreatedResponse(1L)

        assertEquals("something went wrong creating review", response.body)
        assertEquals(500, response.statusCodeValue)
    }
}
