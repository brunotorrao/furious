package com.github.brunotorrao.furious.ports

import arrow.core.None
import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import com.github.brunotorrao.furious.ports.ExternalMoviePort
import com.github.brunotorrao.furious.stubForImdbGet
import com.github.brunotorrao.furious.stubForImdbGetIncorrectImdbId
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

@ExperimentalCoroutinesApi
@WireMockTest(httpPort = 8081)
internal class ExternalMoviePortTest {

    lateinit var externalMoviePort: ExternalMoviePort

    val client: WebClient = WebClient.builder()
        .baseUrl("http://localhost:8081/?apikey=1234")
        .build()

    @BeforeEach
    fun init() {
        externalMoviePort = ExternalMoviePort(client)
    }

    @Test
    fun `given imdb movie when get details then should return external movie details`() = runBlocking {

            stubForImdbGet("tt0232500")

            val details = externalMoviePort.getDetails("tt0232500")

            assertEquals(simpleExternalMovieDetails(), details.orNull())
    }

    @Test
    fun `given non existing imdb movie when get details then should return none`() = runBlocking {

        stubForImdbGetIncorrectImdbId("tt0232450")

        val details = externalMoviePort.getDetails("tt0232450")

        assertEquals(None, details)
    }
}
