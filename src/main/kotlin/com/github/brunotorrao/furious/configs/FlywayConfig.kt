package com.github.brunotorrao.furious.configs

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfig(val env: Environment) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway = Flyway(
        Flyway.configure()
            .baselineOnMigrate(true)
            .locations("classpath:db/migration")
            .dataSource(
                env.getRequiredProperty("spring.flyway.url"),
                env.getRequiredProperty("spring.flyway.user"),
                env.getRequiredProperty("spring.flyway.password")
            )
    )
}
