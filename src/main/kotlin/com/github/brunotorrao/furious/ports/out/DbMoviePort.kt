package com.github.brunotorrao.furious.ports.out

import com.github.brunotorrao.furious.domain.Movie
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DbMoviePort : ReactiveCrudRepository<Movie, Long>
