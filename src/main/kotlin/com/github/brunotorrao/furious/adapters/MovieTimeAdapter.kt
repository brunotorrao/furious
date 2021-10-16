package com.github.brunotorrao.furious.adapters

import arrow.core.Either
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.getOrHandle
import arrow.core.left
import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeGenericException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeMovieNotFounExcetpion
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import java.net.URI

fun Option<Either<MovieTimeException, MovieTime>>.toResponse() =
    this.getOrElse { MovieTimeNotFoundException.left() }
        .map { ok(it) }
        .getOrHandle { handleErrorResponse(it) }

fun Either<MovieTimeException, MovieTime>.toCreatedResponse(movieId: Long) = this
    .map { created(URI.create("/movies/$movieId/times/${it.id}")).body(it)  }
    .getOrHandle { handleErrorResponse(it) }


fun handleErrorResponse(exception: MovieTimeException) = when (exception) {
    MovieTimeConflict -> status(CONFLICT).body("date for the movie already exists")
    MovieTimeGenericException -> status(INTERNAL_SERVER_ERROR).body("something went wrong creating movie time")
    MovieTimeNotFoundException -> status(NOT_FOUND).body("movie time not found")
    MovieTimeMovieNotFounExcetpion -> status(NOT_FOUND).body("movie not found")
}
