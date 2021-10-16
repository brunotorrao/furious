package com.github.brunotorrao.furious.ports

import arrow.core.left
import com.github.brunotorrao.furious.domain.exceptions.ReviewException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewConflictException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewGenericException
import com.github.brunotorrao.furious.domain.exceptions.ReviewException.ReviewMovieNotFoundException
import com.github.brunotorrao.furious.fixtures.simpleReview
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class DbReviewPortTest {

    lateinit var port: DbReviewPort
    @MockK
    lateinit var repository: ReviewRepository


    @BeforeEach
    fun init() {
        port = DbReviewPort(repository)
    }

    @Test
    fun `given movie id when reviews exists then should find all reviews`() = runBlockingTest {
        coEvery { repository.findAllByMovieId(eq(1L))} returns flowOf(simpleReview())

        val reviews = port.findAllByMovieId(1L)

        assertEquals(1, reviews.toList().size)
        assertEquals(simpleReview(), reviews.first())
    }

    @Test
    fun `given customer id when reviews exists then should find all reviews`() = runBlockingTest {
        coEvery { repository.findAllByCustomerId(eq(1L))} returns flowOf(simpleReview())

        val reviews = port.findAllByCustomer(1L)

        assertEquals(1, reviews.toList().size)
        assertEquals(simpleReview(), reviews.first())
    }

    @Test
    fun `given review to save when no error then should be able to save`() = runBlockingTest {
        coEvery { repository.save(eq(simpleReview()))} returns simpleReview().toMono()

        val review = port.save(simpleReview())

        assertEquals(simpleReview(), review.orNull())
    }

    @Test
    fun `given review to save when saving throws index violation then should return conflict`() = runBlockingTest {
        coEvery { repository.save(eq(simpleReview()))} returns Mono.error(DataIntegrityViolationException("Unique index or primary key violation"))

        val result = port.save(simpleReview())

        assertEquals(ReviewConflictException.left(), result)
    }

    @Test
    fun `given review to save when saving throws foreign key missing then should return movie not found`() = runBlockingTest {
        coEvery { repository.save(eq(simpleReview())) } returns Mono.error(
            DataIntegrityViolationException(
                "Referential integrity constraint violation: " +
                    "\"CONSTRAINT_8F: PUBLIC.REVIEW FOREIGN KEY(MOVIE_ID) REFERENCES PUBLIC.MOVIE(ID) (15)"))

        val result = port.save(simpleReview())

        assertEquals(ReviewMovieNotFoundException.left(), result)
    }

    @Test
    fun `given review to save when saving throws unknow error then should return generic exception`() = runBlockingTest {
        coEvery { repository.save(eq(simpleReview())) } returns Mono.error(DataIntegrityViolationException(""))

        val result = port.save(simpleReview())

        assertEquals(ReviewGenericException.left(), result)
    }
}
