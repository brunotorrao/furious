package com.github.brunotorrao.furious.controllers

import com.github.brunotorrao.furious.domain.Movie
import com.github.brunotorrao.furious.ports.out.DbMoviePort
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

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class MovieControllerTest {

    lateinit var controller: MovieController
    @MockK
    lateinit var dbMoviePort: DbMoviePort

    @BeforeEach
    fun init() {
        controller = MovieController(dbMoviePort)
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

}
