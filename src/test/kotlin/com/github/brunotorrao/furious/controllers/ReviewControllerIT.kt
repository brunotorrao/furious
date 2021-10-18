package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.IntegrationTest
import com.github.brunotorrao.furious.fixtures.jsonForReview
import com.github.brunotorrao.furious.fixtures.jsonForReviewCreated
import com.github.brunotorrao.furious.fixtures.jsonForReviews
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters.fromValue


class ReviewControllerIT : IntegrationTest() {

    @Autowired
    lateinit var webTestClient: WebTestClient



    @Test
    @Order(1)
    fun `given review when is valid then should save`() {
        webTestClient.post()
            .uri { it.path("/movies/1/reviews").build() }
            .body(fromValue(jsonForReview()))
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .isCreated
            .expectBody()
            .json(jsonForReviewCreated())
    }

    @Test
    @Order(2)
    fun `given movie id when exists reviews for this movie then should return all reviews`() {

        webTestClient.get()
            .uri { it.path("/movies/1/reviews").build() }
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForReviews())
    }

    @Test
    @Order(2)
    fun `given customer id when exists reviews for this customer then should return all reviews`() {

        webTestClient.get()
            .uri { it.path("/reviews").queryParam("customerId", 1).build() }
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .json(jsonForReviews())
    }

}
