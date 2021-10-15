package com.github.brunotorrao.furious.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@ConfigurationProperties("api")
class WebClientConfig {

    lateinit var omdb: String

    @Bean
    fun omdbWebClient() = WebClient.builder()
            .baseUrl(omdb)
            .build()
}
