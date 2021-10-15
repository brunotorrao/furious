package com.github.brunotorrao.furious.controllers

import arrow.core.toOption
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import com.github.brunotorrao.furious.fixtures.simpleMovieDetails
import com.github.brunotorrao.furious.ports.out.DbMoviePort
import com.github.brunotorrao.furious.ports.out.ExternalMoviePort
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class MovieControllerTest {

    lateinit var controller: MovieController
    @MockK
    lateinit var dbMoviePort: DbMoviePort
    @MockK
    lateinit var externalMoviePort: ExternalMoviePort

    @BeforeEach
    fun init() {
        controller = MovieController(dbMoviePort, externalMoviePort)
    }

    @Test
    fun `given movies when getting all then should return all movies`() = runBlockingTest {
        every { dbMoviePort.findAll() } returns Flux.just(Movie(1, "The Fast and the Furious", "tt0232500"))

        val allMovies = controller.getAllMovies()

        assertEquals(allMovies.first(), Movie(1, "The Fast and the Furious", "tt0232500"))
    }

    @Test
    fun `given no movies when getting all then should return empty list`() = runBlockingTest {
        every { dbMoviePort.findAll() } returns Flux.empty()

        val allMovies = controller.getAllMovies()

        assertEquals(allMovies.toList(), listOf<Movie>())
    }

    @Test
    fun `given a movie when get details then should return the movie details`() = runBlockingTest {
        val movieId = 1L
        val movie = Movie(movieId, "The Fast and the Furious", "tt0232500")
        val details = simpleMovieDetails()
        val externalMovieDetails = simpleExternalMovieDetails()

        every { dbMoviePort.findById( eq(movieId)) } returns Mono.just(movie)
        coEvery { externalMoviePort.getDetails(eq(movie.externalId)) } returns externalMovieDetails.toOption()

        val detailsResult = controller.getMovieDetailsById(movieId)

        assertEquals(details, detailsResult.orNull())
    }

    @Test
    fun `given a non existing movie when get details then should return empty`() = runBlockingTest {
        val movieId = 1L
        val movie = Movie(movieId, "The Fast and the Furious", "tt0232500")

        every { dbMoviePort.findById( eq(movieId)) } returns Mono.empty()
        coVerify(exactly = 0)  { externalMoviePort.getDetails(eq(movie.externalId)) }

        val detailsResult = controller.getMovieDetailsById(movieId)

        assertEquals(true, detailsResult.isEmpty())
    }

}
