package com.github.brunotorrao.furious.ports.`in`

import arrow.core.getOrHandle
import com.github.brunotorrao.furious.controllers.MovieController
import com.github.brunotorrao.furious.domain.exceptions.MovieException
import com.github.brunotorrao.furious.domain.exceptions.MovieException.MovieNotFoundException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpMoviePort(
    private val movieController: MovieController
) {

    @GetMapping("/movies")
    @ResponseStatus(OK)
    suspend fun getAllMovies() = movieController.getAllMovies()

    @GetMapping("/movies/{id}")
    suspend fun getMovieDetailsById(@PathVariable("id") id: Long) = movieController.getMovieDetailsById(id)
        .map { ok(it) }
        .getOrHandle { handleErrorResponse(it) }

    fun handleErrorResponse(exception: MovieException) = when (exception) {
        MovieNotFoundException -> ResponseEntity.status(NOT_FOUND).body("movie not found")
    }

}
