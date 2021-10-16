package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.adapters.toCreatedResponse
import com.github.brunotorrao.furious.adapters.toResponse
import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.MovieTimeUpdate
import com.github.brunotorrao.furious.logic.MovieTimeLogic.prepareSave
import com.github.brunotorrao.furious.logic.MovieTimeLogic.prepareUpdate
import com.github.brunotorrao.furious.ports.DbMovieTimePort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/movies")
class MovieTimeController(
    private val dbMovieTimePort: DbMovieTimePort
) {

    @GetMapping("/{movieId}/times")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getMovieTimes(@PathVariable("movieId") movieId: Long) =
        dbMovieTimePort.findAllByMovieIdOrderByDateDesc(movieId)

    @PostMapping("/{movieId}/times")
    suspend fun createMovieTime(@PathVariable("movieId") movieId: Long, @RequestBody movieTime: MovieTime) =
        prepareSave(movieId, movieTime)
            .let { dbMovieTimePort.save(it) }
            .toCreatedResponse(movieId)

    @PatchMapping("/{movieId}/times/{id}")
    suspend fun updatePriceAndDateTime(
        @PathVariable("movieId") movieId: Long,
        @PathVariable("id") id: Long,
        @RequestBody movieTimeUpdate: MovieTimeUpdate
    ) =
        dbMovieTimePort.findByIdAndMovieId(id, movieId)
            .map { prepareUpdate(it, movieTimeUpdate) }
            .map { dbMovieTimePort.save(it) }
            .toResponse()
}
