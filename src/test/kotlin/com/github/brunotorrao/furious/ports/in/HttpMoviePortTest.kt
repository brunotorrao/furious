package com.github.brunotorrao.furious.ports.`in`

import com.github.brunotorrao.furious.controllers.MovieController
import com.github.brunotorrao.furious.domain.Movie
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
}
