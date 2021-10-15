package com.github.brunotorrao.furious.ports.`in`

import arrow.core.Either.Left
import arrow.core.right
import com.github.brunotorrao.furious.controllers.MovieController
import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.domain.exceptions.MovieException.MovieNotFoundException
import com.github.brunotorrao.furious.fixtures.simpleMovieDetails
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class HttpMoviePortTest {

    lateinit var httpPort: HttpMoviePort
    @MockK
    lateinit var movieController: MovieController

    @BeforeEach
    fun init() {
        httpPort = HttpMoviePort(movieController)
    }

    @Test
    fun `given movies when getting all then should return all movies`() = runBlockingTest {
        coEvery { movieController.getAllMovies() } returns flowOf(Movie(1, "The Fast and the Furious", "tt0232500"))

        val allMovies = httpPort.getAllMovies()

        assertEquals(allMovies.first(), Movie(1, "The Fast and the Furious", "tt0232500"))
    }

    @Test
    fun `given movie id when get details then should get movie details`() = runBlockingTest {
        coEvery { movieController.getMovieDetailsById(eq(1L)) } returns simpleMovieDetails().right()

        val response = httpPort.getMovieDetailsById(1L)

        assertEquals(simpleMovieDetails(), response.body)
        assertEquals(OK, response.statusCode)
    }

    @Test
    fun `given movie id when movie does not exists then should return not found`() = runBlockingTest {
        coEvery { movieController.getMovieDetailsById(eq(1L)) } returns Left(MovieNotFoundException)

        val response = httpPort.getMovieDetailsById(1L)

        assertEquals(NOT_FOUND, response.statusCode)
        assertEquals("movie not found", response.body)
    }
}
