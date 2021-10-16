package com.github.brunotorrao.furious.controllers

import arrow.core.right
import com.github.brunotorrao.furious.fixtures.invalidReview
import com.github.brunotorrao.furious.fixtures.simpleReview
import com.github.brunotorrao.furious.ports.DbReviewPort
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class ReviewControllerTest {

    lateinit var controller: ReviewController

    @MockK
    lateinit var dbReviewPort: DbReviewPort

    @BeforeEach
    fun init() {
        controller = ReviewController(dbReviewPort)
    }

    @Test
    fun `given reviews for a customer id when exists then should return all reviews`() = runBlockingTest {
        coEvery { dbReviewPort.findAllByCustomer(eq(1L)) } returns flowOf(simpleReview())

        val reviews = controller.findAllByCustomer(1L)

        assertEquals(1, reviews.toList().size)
        assertEquals(simpleReview(), reviews.first())
    }

    @Test
    fun `given reviews for a customer id when does not exists then should return empty`() = runBlockingTest {
        coEvery { dbReviewPort.findAllByCustomer(eq(1L)) } returns emptyFlow()

        val reviews = controller.findAllByCustomer(1L)

        assertTrue(reviews.toList().isEmpty())
    }

    @Test
    fun `given reviews for a movie id when exists then should return all reviews`() = runBlockingTest {
        coEvery { dbReviewPort.findAllByMovieId(eq(1L)) } returns flowOf(simpleReview())

        val reviews = controller.findAllByMovie(1L)

        assertEquals(1, reviews.toList().size)
        assertEquals(simpleReview(), reviews.first())
    }

    @Test
    fun `given reviews for a movie id when does not exists then should return empty`() = runBlockingTest {
        coEvery { dbReviewPort.findAllByMovieId(eq(1L)) } returns emptyFlow()

        val reviews = controller.findAllByMovie(1L)

        assertTrue(reviews.toList().isEmpty())
    }

    @Test
    fun `given review when call to save then should validate prepare and save`() = runBlockingTest {
        val review = simpleReview()

        coEvery { dbReviewPort.save(eq(review.copy(id = 0L))) } returns review.right()

        val result = controller.saveReview(1L, review)

        assertEquals(review, result.body)
        assertEquals(201, result.statusCodeValue)
    }

    @Test
    fun `given review when invalid then should not save`() = runBlockingTest {
        val review = invalidReview()

        coVerify(exactly = 0) { dbReviewPort.save(any()) }

        val result = controller.saveReview(1L, review)

        assertEquals("review rating must be between 1 and 5", result.body)
        assertEquals(400, result.statusCodeValue)
    }
}
