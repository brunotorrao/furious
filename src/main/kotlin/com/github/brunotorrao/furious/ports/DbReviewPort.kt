package com.github.brunotorrao.furious.ports

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.github.brunotorrao.furious.domain.Review
import com.github.brunotorrao.furious.domain.exceptions.ReviewException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.*
import com.github.brunotorrao.furious.extensions.foreignKeyMissing
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
class DbReviewPort(
    private val repository: ReviewRepository
) {

    companion object {
        val log = KotlinLogging.logger {}
    }

    suspend fun findAllByCustomer(customerId: Long) = repository.findAllByCustomerId(customerId)

    suspend fun findAllByMovieId(movieId: Long) = repository.findAllByMovieId(movieId)

    suspend fun save(review: Review): Either<ReviewException, Review> = repository.save(review)
        .map { toEither(it) }
        .onErrorResume { mapError(it) }
        .awaitSingle()

    private fun mapError(err: Throwable): Mono<Either<ReviewException, Review>> {
        log.error { "error creating movie time exception=$err" }
        return when {
            err.isConflict() -> ReviewConflictException.left().toMono()
            err.foreignKeyMissing("movie") -> ReviewMovieNotFoundException.left().toMono()
            else -> ReviewGenericException.left().toMono()
        }
    }

    private fun toEither(review: Review): Either<ReviewException, Review> {
        return review.right()
    }
}

@Repository
interface ReviewRepository : ReactiveCrudRepository<Review, Long> {
    suspend fun findAllByCustomerId(customerId: Long): Flow<Review>
    suspend fun findAllByMovieId(movieId: Long): Flow<Review>
}
