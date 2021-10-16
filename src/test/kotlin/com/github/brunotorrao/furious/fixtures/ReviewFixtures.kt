package com.github.brunotorrao.furious.fixtures

import com.github.brunotorrao.furious.domain.Review

fun simpleReview() = Review(
    1L, 1L, 1L, 5
)


fun invalidReview() = Review(
    1L, 1L, 1L, 6
)
