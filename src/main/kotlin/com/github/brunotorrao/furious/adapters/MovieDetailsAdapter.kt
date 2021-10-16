package com.github.brunotorrao.furious.adapters

import arrow.core.Option
import arrow.core.getOrElse
import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.ExternalMovieRating
import com.github.brunotorrao.furious.domain.MovieDetails
import com.github.brunotorrao.furious.domain.MovieRating
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


fun Option<MovieDetails>.toResponse() = this.map { ResponseEntity.ok(it) }
    .getOrElse { ResponseEntity.status(HttpStatus.NOT_FOUND).body("movie not found") }

fun Option<ExternalMovieDetails>.toMovieDetails(id: Long) = this.map {
    MovieDetails(
        id = id,
        title = it.title,
        year = it.year,
        rated = it.rated,
        released = it.released,
        runtime = it.runtime,
        genres = it.genre.split(", "),
        director = it.director,
        writers = it.writer.split(", "),
        actors = it.actors.split(", "),
        plot = it.plot,
        language = it.language.split(", "),
        countrys = it.country.split(", "),
        awards = it.awards,
        poster = it.poster,
        ratings = it.ratings.map { toMovieRating(it) }.toSet(),
        metascore = it.metascore.toInt(),
        imdbRating = it.imdbRating.toDouble(),
        imdbVotes = it.imdbVotes.replace(",", "").toInt(),
        imdbId = it.imdbId,
        type = it.type,
        dvd = it.dvd,
        boxOffice = it.boxOffice,
        production = it.production,
        website = it.website,
        response = it.response
    )
}

fun toMovieRating(externalMovieRating: ExternalMovieRating) = MovieRating(
    source = externalMovieRating.source,
    value = externalMovieRating.value
)
