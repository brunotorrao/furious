package com.github.brunotorrao.furious

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FuriousApp

fun main(args: Array<String>) {
	runApplication<FuriousApp>(*args)
}
