package com.github.brunotorrao.furious.configs

import org.testcontainers.containers.PostgreSQLContainer

class KPostgreSQLContainer(image: String) : PostgreSQLContainer<KPostgreSQLContainer>(image) {
    override fun start() {
        super.start()
        System.setProperty("spring.flyway.url", this.jdbcUrl)
        System.setProperty("spring.flyway.user", this.username)
        System.setProperty("spring.flyway.password", this.password)
        System.setProperty("spring.r2dbc.url", this.jdbcUrl.replace("jdbc", "r2dbc"))
        System.setProperty("spring.r2dbc.username", this.username)
        System.setProperty("spring.r2dbc.password", this.password)
    }

    companion object {
        val sharedPostgreSQLContainer = KPostgreSQLContainer("postgres:11.1").apply {
            withReuse(true)
            withDatabaseName("furious")
            withUsername("furious")
            withPassword("furious")
            withExposedPorts(5432)
            withCommand("postgres -c max_connections=200")
        }
    }

}
