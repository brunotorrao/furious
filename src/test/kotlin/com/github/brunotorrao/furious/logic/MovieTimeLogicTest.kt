package com.github.brunotorrao.furious.logic

import com.github.brunotorrao.furious.domain.MovieTimeUpdate
import com.github.brunotorrao.furious.fixtures.simpleMovieTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class MovieTimeLogicTest {

    @Test
    fun `given movie time when preparing to save then should remove id and set movie id`() {
        val movieTime = MovieTimeLogic.prepareSave(2L, simpleMovieTime())

        assertEquals(simpleMovieTime(0L, 2L), movieTime)
    }

    @Test
    fun `given movie time update when preparing to update then should only copy price and date`() {
        val date = LocalDateTime.of(2021, 10, 16, 20, 50)
        val price = 19.5.toBigDecimal()
        val update = MovieTimeUpdate(price, date)

        val movieTime = MovieTimeLogic.prepareUpdate(simpleMovieTime(), update)


        assertEquals(simpleMovieTime(price, date), movieTime)


    }

}
