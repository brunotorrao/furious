package com.github.brunotorrao.furious.logic

import com.github.brunotorrao.furious.domain.MovieTime
import com.github.brunotorrao.furious.domain.MovieTimeUpdate

object MovieTimeLogic {

    fun prepareUpdate(movieTime: MovieTime, movieTimeUpdate: MovieTimeUpdate) = movieTime.copy(
        price = movieTimeUpdate.price,
        date = movieTimeUpdate.date
    )

    fun prepareSave(movieId: Long, movieTime: MovieTime) = movieTime.copy(id = 0L, movieId = movieId)
}
