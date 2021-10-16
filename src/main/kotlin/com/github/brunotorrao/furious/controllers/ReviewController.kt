package com.github.brunotorrao.furious.controllers

import arrow.core.flatMap
import com.github.brunotorrao.furious.adapters.toCreatedResponse
import com.github.brunotorrao.furious.domain.Review
import com.github.brunotorrao.furious.logic.ReviewLogic.isValid
import com.github.brunotorrao.furious.logic.ReviewLogic.prepareSave
import com.github.brunotorrao.furious.ports.DbReviewPort
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewController(
    private val dbReviewPort: DbReviewPort
) {

    @PostMapping("/movies/{movieId}/reviews")
    suspend fun saveReview(@PathVariable("movieId") movieId: Long, @RequestBody review: Review) =
        isValid(review)
            .map { prepareSave(it, movieId) }
            .flatMap { dbReviewPort.save(it) }
            .toCreatedResponse(movieId)

    @ResponseStatus(OK)
    @GetMapping("/movies/{movieId}/reviews")
    suspend fun findAllByMovie(@PathVariable("movieId") movieId: Long) = dbReviewPort.findAllByMovieId(movieId)

    @ResponseStatus(OK)
    @GetMapping("/reviews", params = ["customerId"])
    suspend fun findAllByCustomer(@RequestParam("customerId") customerId: Long) =
        dbReviewPort.findAllByCustomer(customerId)
}
