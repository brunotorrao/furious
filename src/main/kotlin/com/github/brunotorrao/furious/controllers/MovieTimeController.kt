package com.github.brunotorrao.furious.controllers

import arrow.core.Either
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.left
import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.MovieTimeUpdate
import com.github.brunotorrao.furious.domain.exceptions.MovieException.MovieNotFoundException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeNotFoundException
import com.github.brunotorrao.furious.logic.MovieTimeLogic.prepareSave
import com.github.brunotorrao.furious.logic.MovieTimeLogic.prepareUpdate
import com.github.brunotorrao.furious.ports.out.DbMovieTimePort
import org.springframework.stereotype.Component

@Component
class MovieTimeController(
    private val dbMovieTimePort: DbMovieTimePort
) {

    suspend fun getMovieTimes(movieId: Long) = dbMovieTimePort.findAllByMovieIdOrderByDateDesc(movieId)

    suspend fun createMovieTime(movieId: Long, movieTime: MovieTime) : Either<MovieTimeException, MovieTime> = prepareSave(movieId, movieTime)
        .let { dbMovieTimePort.save(it) }

    suspend fun updatePriceAndDateTime(id: Long, movieId: Long, movieTimeUpdate: MovieTimeUpdate): Either<MovieTimeException, MovieTime> =
        dbMovieTimePort.findByIdAndMovieId(id, movieId)
            .map { prepareUpdate(it, movieTimeUpdate) }
            .map { dbMovieTimePort.save(it) }
            .getOrElse { MovieTimeNotFoundException.left() }
}
