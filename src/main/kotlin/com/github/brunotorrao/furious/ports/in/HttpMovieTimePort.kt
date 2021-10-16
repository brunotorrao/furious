package com.github.brunotorrao.furious.ports.`in`

import arrow.core.getOrHandle
import com.github.brunotorrao.furious.controllers.MovieTimeController
import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.MovieTimeUpdate
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeGenericException
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/movies")
class HttpMovieTimePort(
    private val movieTimeController: MovieTimeController
) {

    @GetMapping("/{movieId}/times")
    @ResponseStatus(OK)
    suspend fun getAllMovieTimes(@PathVariable("movieId") id: Long) = movieTimeController.getMovieTimes(id)

    @PostMapping("/{movieId}/times")
    suspend fun createMovieTime(@PathVariable("movieId") movieId: Long, @RequestBody movieTime: MovieTime) =
        movieTimeController.createMovieTime(movieId, movieTime)
            .map { created(URI.create("/movies/$movieId/times/${it.id}")).body(it) }
            .getOrHandle { handleErrorResponse(it) }

    @PatchMapping("/{movieId}/times/{id}")
    suspend fun updatePriceAndDateTime(
        @PathVariable("movieId") movieId: Long,
        @PathVariable("id") id: Long,
        @RequestBody movieTime: MovieTimeUpdate
    ) = movieTimeController.updatePriceAndDateTime(id, movieId, movieTime)
        .getOrHandle { handleErrorResponse(it) }


    fun handleErrorResponse(exception: MovieTimeException) = when (exception) {
        MovieTimeConflict -> status(CONFLICT).body("date for the movie already exists")
        MovieTimeGenericException -> status(INTERNAL_SERVER_ERROR).body("something went wrong creating movie time")
        MovieTimeNotFoundException -> status(NOT_FOUND).body("movie time not found")
    }
}
