package com.github.brunotorrao.furious.controllers

import arrow.core.None
import arrow.core.toOption
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.fixtures.simpleExternalMovieDetails
import com.github.brunotorrao.furious.fixtures.simpleMovieDetails
import com.github.brunotorrao.furious.ports.DbMoviePort
import com.github.brunotorrao.furious.ports.ExternalMovieCachePort
import com.github.brunotorrao.furious.ports.ExternalMoviePort
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
    @MockK
    lateinit var externalMovieCachePort: ExternalMovieCachePort

    @BeforeEach
    fun init() {
        controller = MovieController(dbMoviePort, externalMoviePort, externalMovieCachePort)
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
        coEvery { externalMoviePort.getDetails(eq(movie)) } returns externalMovieDetails.toOption()
        coEvery { externalMovieCachePort.findById(eq(movie)) } returns None
        coEvery { externalMovieCachePort.cache(eq(externalMovieDetails)) } returns externalMovieDetails

        val response = controller.getMovieDetailsById(movieId)

        assertEquals(200, response.statusCodeValue)
        assertEquals(details, response.body)
    }

    @Test
    fun `given a non existing movie when get details then should return empty`() = runBlockingTest {
        val movieId = 1L
        val movie = Movie(movieId, "The Fast and the Furious", "tt0232500")

        every { dbMoviePort.findById( eq(movieId)) } returns Mono.empty()
        coVerify(exactly = 0)  { externalMoviePort.getDetails(eq(movie)) }
        coVerify(exactly = 0) { externalMovieCachePort.findById(any()) }
        coVerify(exactly = 0) { externalMovieCachePort.cache(any()) }

        val response = controller.getMovieDetailsById(movieId)

        assertEquals(404, response.statusCodeValue)
        assertEquals("movie not found", response.body)
    }

    @Test
    fun `given a movie when cached then should return from the cache`() = runBlockingTest {
        val movieId = 1L
        val movie = Movie(movieId, "The Fast and the Furious", "tt0232500")
        val details = simpleMovieDetails()
        val externalMovieDetails = simpleExternalMovieDetails()

        every { dbMoviePort.findById( eq(movieId)) } returns Mono.just(movie)
        coEvery { externalMovieCachePort.findById(eq(movie)) } returns externalMovieDetails.toOption()

        coVerify(exactly = 0) { externalMoviePort.getDetails(any()) }
        coVerify(exactly = 0) { externalMovieCachePort.cache(any()) }

        val response = controller.getMovieDetailsById(movieId)

        assertEquals(200, response.statusCodeValue)
        assertEquals(details, response.body)
    }
}
