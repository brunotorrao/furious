package com.github.brunotorrao.furious.ports

import arrow.core.None
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.fixtures.jsonForExternalMovieDetails
import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.core.publisher.Mono
import java.time.Duration.ofMinutes

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class ExternalMovieCachePortTest {

    lateinit var cache: ExternalMovieCachePort

    @MockK
    lateinit var redisTemplate: ReactiveStringRedisTemplate
    @MockK
    lateinit var objectMapper: ObjectMapper
    @MockK
    lateinit var valueOps: ReactiveValueOperations<String, String>


    @BeforeEach
    fun init() {
        cache = ExternalMovieCachePort(redisTemplate, objectMapper)

        every { redisTemplate.opsForValue() } returns valueOps
    }

    @Test
    fun `given movie when in cache then should return movie details`() = runBlocking {

        val imdbId = "tt0232500"

        every { valueOps.get(eq(imdbId)) } returns Mono.just(jsonForExternalMovieDetails())
        every { objectMapper.readValue(eq(jsonForExternalMovieDetails()), eq(ExternalMovieDetails::class.java)) } returns simpleExternalMovieDetails()

        val details = cache.findById(Movie(1L, "The Fast and The Furious", imdbId))

        assertEquals(simpleExternalMovieDetails(), details.orNull())
    }

    @Test
    fun `given movie when not in cache then should return none`() = runBlocking {

        val imdbId = "tt0232500"

        every { valueOps.get(eq(imdbId)) } returns Mono.empty()

        val details = cache.findById(Movie(1L, "The Fast and The Furious", imdbId))

        assertEquals(None, details)
    }

    @Test
    fun `given movie when caching then should put in cache and return details`() = runBlocking {

        val movieDetails = simpleExternalMovieDetails()

        every { objectMapper.writeValueAsString(movieDetails) } returns jsonForExternalMovieDetails()

        every { valueOps.set(eq(movieDetails.imdbId), eq(jsonForExternalMovieDetails()), eq(ofMinutes(2))) } returns Mono.just(true)

        val details = cache.cache(movieDetails)

        assertEquals(simpleExternalMovieDetails(), details)
    }

}
