package com.github.brunotorrao.furious.fixtures

import com.github.brunotorrao.furious.domain.MovieTime
import java.math.BigDecimal
import java.time.LocalDateTime

fun simpleMovieTime() = MovieTime(
    1L, 1L, 15.6.toBigDecimal(),
    LocalDateTime.of(2021, 10, 15, 20, 30)
)

fun simpleMovieTime(id: Long, movieId: Long) = MovieTime(
    id, movieId, 15.6.toBigDecimal(),
    LocalDateTime.of(2021, 10, 15, 20, 30)
)

fun simpleMovieTime(price: BigDecimal, date: LocalDateTime) = MovieTime(
    1L, 1L, price, date
)

fun movieTimeWithoutId() = MovieTime(
    0L, 1L, 15.6.toBigDecimal(),
    LocalDateTime.of(2021, 10, 15, 20, 30)
)
