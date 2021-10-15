package com.github.brunotorrao.furious.configs

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration : AbstractR2dbcConfiguration() {

    override fun connectionFactory() = H2ConnectionFactory(
        H2ConnectionConfiguration.builder()
            .url("mem:testdb;DB_CLOSE_DELAY=-1;")
            .username("sa")
            .build()
    )
}
