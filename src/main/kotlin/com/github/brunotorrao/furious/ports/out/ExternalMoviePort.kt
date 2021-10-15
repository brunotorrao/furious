package com.github.brunotorrao.furious.ports.out

import arrow.core.Either
import arrow.core.right
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.exceptions.MovieException
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class ExternalMoviePort(
    private val client: WebClient
) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    suspend fun getDetails(externalId: String): Either<MovieException, ExternalMovieDetails> {
        return client.get()
            .uri { it.queryParam("i", externalId).build() }
            .retrieve()
            .bodyToMono(ExternalMovieDetails::class.java)
            .map(this::toEither)
            .onErrorResume {
                log.error { "error getting movie from imdb exception=$it" }
                Mono.just(Either.Left(MovieException.MovieNotFoundException))
            }
            .awaitSingle()
    }

    private fun toEither(movie: ExternalMovieDetails): Either<MovieException, ExternalMovieDetails> {
        return movie.right()
    }
}
