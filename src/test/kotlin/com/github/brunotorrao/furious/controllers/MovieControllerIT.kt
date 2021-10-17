package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.configs.TestRedisConfiguration
import com.github.brunotorrao.furious.fixtures.jsonForAllMovies
import com.github.brunotorrao.furious.fixtures.jsonForMovieById
import com.github.brunotorrao.furious.stubForImdbGet
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestRedisConfiguration::class])
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8081)
@ActiveProfiles("test")
class MovieControllerIT {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `given the movies exists in database when calling getAllMovies then should return all movies`() {
        webTestClient.get()
            .uri { it.path("/movies").build() }
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForAllMovies())
    }

    @Test
    fun `given movie id when get details then should return movie details`() {

        stubForImdbGet("tt0232500")

        webTestClient.get()
            .uri { it.path("/movies/1").build() }
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForMovieById())
    }

}
