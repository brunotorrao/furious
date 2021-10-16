package com.github.brunotorrao.furious.adapters

import arrow.core.Either
import arrow.core.getOrHandle
import com.github.brunotorrao.furious.domain.Review
import com.github.brunotorrao.furious.domain.exceptions.ReviewException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewConflictException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewGenericException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewMovieNotFoundException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewRatingException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import java.net.URI

fun Either<ReviewException, Review>.toCreatedResponse(movieId: Long) = this
    .map { ResponseEntity.created(URI.create("/movies/$movieId/reviews/${it.id}")).body(it)  }
    .getOrHandle { handleErrorResponse(it) }

fun handleErrorResponse(exception: ReviewException) = when (exception) {
    ReviewConflictException -> status(CONFLICT).body("review for the movie already exists")
    ReviewGenericException -> status(INTERNAL_SERVER_ERROR).body("something went wrong creating review")
    ReviewMovieNotFoundException -> status(NOT_FOUND).body("movie not found")
    ReviewRatingException -> status(BAD_REQUEST).body("review rating must be between 1 and 5")
}
