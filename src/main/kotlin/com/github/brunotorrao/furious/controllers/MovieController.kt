package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.adapters.MovieDetailsAdapter.toMovieDetails
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.extensions.awaitOption
import com.github.brunotorrao.furious.ports.out.DbMoviePort
import com.github.brunotorrao.furious.ports.out.ExternalMoviePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Component

@Component
class MovieController(
    private val dbMoviePort: DbMoviePort,
    private val externalMoviePort: ExternalMoviePort
) {
    suspend fun getAllMovies(): Flow<Movie> = dbMoviePort.findAll().asFlow()

    suspend fun getMovieDetailsById(id: Long) = dbMoviePort.findById(id)
        .awaitOption()
        .flatMap { externalMoviePort.getDetails(it.externalId) }
        .map { toMovieDetails(id, it) }
}
