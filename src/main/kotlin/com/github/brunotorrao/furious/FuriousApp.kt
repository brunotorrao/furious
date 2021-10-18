package com.github.brunotorrao.furious

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableCaching
@SpringBootApplication
@EnableR2dbcRepositories
class FuriousApp

fun main(args: Array<String>) {
	runApplication<FuriousApp>(*args)
}
