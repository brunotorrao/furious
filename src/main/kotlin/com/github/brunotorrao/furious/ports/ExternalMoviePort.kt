package com.github.brunotorrao.furious.ports

import arrow.core.Option
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.extensions.awaitOption
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono.empty

@Component
class ExternalMoviePort(
    private val client: WebClient
) {

    companion object {
        val log = KotlinLogging.logger { }
    }

    suspend fun getDetails(movie: Movie): Option<ExternalMovieDetails> = client.get()
        .uri { it.queryParam("i", movie.externalId).build() }
        .retrieve()
        .bodyToMono(ExternalMovieDetails::class.java)
        .onErrorResume {
            log.info("error calling external api = $it")
            empty()
        }
        .awaitOption()
}
