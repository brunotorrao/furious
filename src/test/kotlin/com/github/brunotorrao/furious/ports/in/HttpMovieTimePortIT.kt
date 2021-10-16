package com.github.brunotorrao.furious.ports.`in`

import com.github.brunotorrao.furious.fixtures.jsonForAllMovieTimes
import com.github.brunotorrao.furious.fixtures.jsonForMovieConflict
import com.github.brunotorrao.furious.fixtures.jsonForMovieCreated
import com.github.brunotorrao.furious.fixtures.jsonForUpdatePriceAndDateTime
import com.github.brunotorrao.furious.fixtures.jsonForUpdatePriceAndDateTimeResult
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters.fromValue


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8081)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation::class)
internal class HttpMovieTimePortIT {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    fun `given the movie times exists in database when calling getAllMovieTimes then should return all movie times`() {
        webTestClient.get()
            .uri { it.path("/movies/1/times").build() }
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForAllMovieTimes())
    }

    @Test
    @Order(2)
    fun `given movie time when calling save then should save correctly`() {
        webTestClient.post()
            .uri { it.path("/movies/1/times").build() }
            .body(fromValue(jsonForMovieCreated()))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .isCreated
            .expectBody()
            .json(jsonForMovieCreated())
    }

    @Test
    @Order(3)
    fun `given movie time to create when already exists then should return conflict`() {
        val result = webTestClient.post()
            .uri { it.path("/movies/1/times").build() }
            .body(fromValue(jsonForMovieConflict()))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .isEqualTo(CONFLICT)
            .expectBody()
            .returnResult().responseBody

        assertEquals("date for the movie already exists", String(result!!))
    }

    @Test
    @Order(4)
    fun `given movie time update when exists then should update`() {
        webTestClient.patch()
            .uri { it.path("/movies/1/times/1").build() }
            .body(fromValue(jsonForUpdatePriceAndDateTime()))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForUpdatePriceAndDateTimeResult())
    }

}
