package com.github.brunotorrao.furious.ports

import arrow.core.Option
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.extensions.awaitOption
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration.ofMinutes

@Component
class ExternalMovieCachePort(
    private val redisTemplate: ReactiveStringRedisTemplate,
    private val objectMapper: ObjectMapper
) {

    suspend fun findById(movie: Movie) : Option<ExternalMovieDetails> = redisTemplate
        .opsForValue()
        .get(movie.externalId)
        .awaitOption()
        .map { objectMapper.readValue(it, ExternalMovieDetails::class.java) }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun cache(movieDetails: ExternalMovieDetails): ExternalMovieDetails = redisTemplate
        .opsForValue()
        .set(movieDetails.imdbId, objectMapper.writeValueAsString(movieDetails), ofMinutes(2))
        .awaitSingle()
        .let { movieDetails }
}
