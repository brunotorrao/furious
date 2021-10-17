package com.github.brunotorrao.furious.controllers

import arrow.core.Option
import arrow.core.orElse
import com.github.brunotorrao.furious.adapters.toMovieDetails
import com.github.brunotorrao.furious.adapters.toResponse
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.extensions.awaitOption
import com.github.brunotorrao.furious.ports.DbMoviePort
import com.github.brunotorrao.furious.ports.ExternalMovieCachePort
import com.github.brunotorrao.furious.ports.ExternalMoviePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(
    private val dbMoviePort: DbMoviePort,
    private val externalMoviePort: ExternalMoviePort,
    private val cachePort: ExternalMovieCachePort
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/movies")
    suspend fun getAllMovies(): Flow<Movie> = dbMoviePort.findAll().asFlow()

    @GetMapping("/movies/{id}")
    suspend fun getMovieDetailsById(@PathVariable("id") id: Long) = dbMoviePort.findById(id).awaitOption()
            .flatMap { getDetails(it) }
            .toMovieDetails(id)
            .toResponse()

    private suspend fun getDetails(movie: Movie): Option<ExternalMovieDetails> = cachePort.findById(movie)
            .orElse { externalMoviePort.getDetails(movie)
                .map { cachePort.cache(it) } }

}
