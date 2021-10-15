package com.github.brunotorrao.furious.ports.`in`

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureWebTestClient
class HttpMoviePortIT {

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
            .json("""
                [
                  {
                    "id": 1,
                    "title": "The Fast and the Furious",
                    "externalId": "tt0232500"
                  },
                  {
                    "id": 2,
                    "title": "2 Fast 2 Furious",
                    "externalId": "tt0322259"
                  },
                  {
                    "id": 3,
                    "title": "The Fast and the Furious: Tokyo Drift",
                    "externalId": "tt0463985"
                  },
                  {
                    "id": 4,
                    "title": "Fast & Furious",
                    "externalId": "tt1013752"
                  },
                  {
                    "id": 5,
                    "title": "Fast Five",
                    "externalId": "tt1596343"
                  },
                  {
                    "id": 6,
                    "title": "Fast & Furious 6",
                    "externalId": "tt1905041"
                  },
                  {
                    "id": 7,
                    "title": "Furious 7",
                    "externalId": "tt2820852"
                  },
                  {
                    "id": 8,
                    "title": "The Fate of the Furious",
                    "externalId": "tt4630562"
                  }
                ]
            """.trimIndent())
    }

}
