package com.github.brunotorrao.furious.extensions

import arrow.core.Option
import arrow.core.toOption
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingleOrNull
import reactor.core.publisher.Mono

suspend fun <T> Mono<T>.awaitSingleOption(): Option<T> = coroutineScope {
    this@awaitSingleOption.awaitSingleOrNull().toOption()
}
