package com.github.brunotorrao.furious.domain

import arrow.core.Either
import arrow.core.right
import com.github.brunotorrao.furious.domain.exceptions.MovieException
import org.springframework.data.annotation.Id

data class Movie(
    @Id
    val id: Long = 0,
    val title: String,
    val externalId: String
) {
    fun toEither(): Either<MovieException, Movie> = this.right()
}
