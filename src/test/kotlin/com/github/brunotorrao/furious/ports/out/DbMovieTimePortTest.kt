package com.github.brunotorrao.furious.ports.out

import arrow.core.left
import com.github.brunotorrao.furious.domain.exceptions.MovieTimeException.MovieTimeConflict
import com.github.brunotorrao.furious.fixtures.simpleMovieTime
import io.mockk.coEvery
import io.mockk.every
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
internal class DbMovieTimePortTest {

    lateinit var port: DbMovieTimePort
    @MockK
    lateinit var repository: MovieTimeRepository


    @BeforeEach
    fun init() {
        port = DbMovieTimePort(repository)
    }

    @Test
    fun `given id and movie id when exists then should find movie time`() = runBlockingTest {
        every { repository.findByIdAndMovieId(eq(1L), eq(1L))} returns simpleMovieTime().toMono()

        val movieTime = port.findByIdAndMovieId(1L, 1L)

        assertEquals(simpleMovieTime(), movieTime.orNull())
    }

    @Test
    fun `given movie id when exists then should find all movie times`() = runBlockingTest {
        coEvery { repository.findAllByMovieIdOrderByDateDesc(eq(1L))} returns flowOf(simpleMovieTime())

        val movieTime = port.findAllByMovieIdOrderByDateDesc(1L)

        assertEquals(1, movieTime.toList().size)
        assertEquals(simpleMovieTime(), movieTime.first())
    }

    @Test
    fun `given movie to save when no error then should accomplish to save`() = runBlockingTest {
        coEvery { repository.save(eq(simpleMovieTime()))} returns simpleMovieTime().toMono()

        val movieTime = port.save(simpleMovieTime())

        assertEquals(simpleMovieTime(), movieTime.orNull())
    }

    @Test
    fun `given movie to save when saving throws an error then should return error mapped`() = runBlockingTest {
        coEvery { repository.save(eq(simpleMovieTime()))} returns Mono.error(DataIntegrityViolationException("Unique index or primary key violation"))

        val movieTime = port.save(simpleMovieTime())

        assertEquals(MovieTimeConflict.left(), movieTime)
    }

}
