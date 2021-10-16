package com.github.brunotorrao.furious.ports

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeGenericException
import com.github.brunotorrao.furious.extensions.awaitOption
import com.github.brunotorrao.furious.extensions.isConflict
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DbMovieTimePort(
    private val repository: MovieTimeRepository
) {

    companion object {
        val log = KotlinLogging.logger {}
    }

    suspend fun findByIdAndMovieId(id: Long, movieId: Long) = repository.findByIdAndMovieId(id, movieId)
        .awaitOption()

    suspend fun findAllByMovieIdOrderByDateDesc(movieId: Long) = repository.findAllByMovieIdOrderByDateDesc(movieId)

    suspend fun save(movieTime: MovieTime) : Either<MovieTimeException, MovieTime> {
        return repository.save(movieTime)
            .map { toEither(it) }
            .onErrorResume { mapError(it) }
            .awaitSingle()
    }

    private fun mapError(err: Throwable) : Mono<Either<MovieTimeException, MovieTime>> {
        log.error { "error creating movie time exception=$err" }
        return when {
            err.isConflict() -> MovieTimeConflict.left().toMono()
            else -> MovieTimeGenericException.left().toMono()
        }
    }

    private fun toEither(movieTime: MovieTime): Either<MovieTimeException, MovieTime> {
        return movieTime.right()
    }
}

@Repository
interface MovieTimeRepository : ReactiveCrudRepository<MovieTime, Long> {

    suspend fun findAllByMovieIdOrderByDateDesc(movieId: Long): Flow<MovieTime>

    fun findByIdAndMovieId(id: Long, movieId: Long): Mono<MovieTime>
}
