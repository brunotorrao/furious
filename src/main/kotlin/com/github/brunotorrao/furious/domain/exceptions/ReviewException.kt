package com.github.brunotorrao.furious.domain.exceptions

sealed class ReviewException {
    object ReviewMovieNotFoundException: ReviewException()
    object ReviewConflictException: ReviewException()
    object ReviewGenericException: ReviewException()
    object ReviewRatingException: ReviewException()
}
