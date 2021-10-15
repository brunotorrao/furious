package com.github.brunotorrao.furious.ports.`in`

import com.github.brunotorrao.furious.controllers.MovieController
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpMoviePort(
    private val movieController: MovieController
) {

    @GetMapping("/movies")
    @ResponseStatus(OK)
    suspend fun getAllMovies() = movieController.getAllMovies()


}
