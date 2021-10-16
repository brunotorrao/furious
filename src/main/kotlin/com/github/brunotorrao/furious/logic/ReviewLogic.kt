package com.github.brunotorrao.furious.logic

import arrow.core.left
import arrow.core.right
import com.github.brunotorrao.furious.domain.Review
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewRatingException

object ReviewLogic {

    fun isValid(review: Review) = if (review.rating in 1..5)
        review.right()
    else ReviewRatingException.left()

    fun prepareSave(review: Review, movieId: Long) = review.copy(id = 0L, movieId = movieId)
}
