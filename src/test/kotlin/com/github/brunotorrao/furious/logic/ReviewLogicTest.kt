package com.github.brunotorrao.furious.logic

import arrow.core.left
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewRatingException
import com.github.brunotorrao.furious.fixtures.invalidReview
import com.github.brunotorrao.furious.fixtures.simpleReview
import com.github.brunotorrao.furious.logic.ReviewLogic.isValid
import com.github.brunotorrao.furious.logic.ReviewLogic.prepareSave
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ReviewLogicTest {

    @Test
    fun `given review when is valid then should return review`() {
        val result = isValid(simpleReview())

        assertEquals(simpleReview(), result.orNull())
    }

    @Test
    fun `given review when is not valid then should return rating exception`() {
        val result = isValid(invalidReview())

        assertEquals(ReviewRatingException.left(), result)
    }

    @Test
    fun `given review when preparing to save then should remove id and update movie id`() {
        val expectedReview = simpleReview().copy(id = 0L, movieId = 10L)

        val review = prepareSave(simpleReview(), 10L)

        assertEquals(expectedReview, review)

    }

}
