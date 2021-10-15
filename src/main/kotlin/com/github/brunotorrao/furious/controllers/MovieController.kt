package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.ports.out.DbMoviePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Component

@Component
class MovieController(
    private val dbMoviePort: DbMoviePort
) {
    suspend fun getAllMovies(): Flow<Movie> = dbMoviePort.findAll().asFlow()
}
