package com.github.brunotorrao.furious

import com.github.brunotorrao.furious.configs.KPostgreSQLContainer
import com.github.brunotorrao.furious.configs.TestRedisConfiguration
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestRedisConfiguration::class])
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Testcontainers
@DirtiesContext
abstract class IntegrationTest {

    companion object {
        @Container
        val postgreSQLContainer = KPostgreSQLContainer.sharedPostgreSQLContainer
    }
}
