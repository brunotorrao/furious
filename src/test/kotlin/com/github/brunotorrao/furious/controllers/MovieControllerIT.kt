package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.IntegrationTest
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.fixtures.jsonForAllMovies
import com.github.brunotorrao.furious.fixtures.jsonForMovieById
import com.github.brunotorrao.furious.ports.DbMoviePort
import com.github.brunotorrao.furious.stubForImdbGet
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.reactive.server.WebTestClient

@WireMockTest(httpPort = 8081)
class MovieControllerIT : IntegrationTest() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var dbMoviePort: DbMoviePort

    @Test
    @Order(1)
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
    @Order(2)
    fun `given movie id when get details then should return movie details`() {

        val movie = dbMoviePort.save(Movie(0L, "fake title", "imdbidfake"))
            .block()

        stubForImdbGet("imdbidfake")

        webTestClient.get()
            .uri { it.path("/movies/${movie?.id}").build() }
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForMovieById(movie!!.id))
    }

}
