package com.github.brunotorrao.furious.adapters

import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.ExternalMovieRating
import com.github.brunotorrao.furious.domain.MovieDetails
import com.github.brunotorrao.furious.domain.MovieRating

object MovieDetailsAdapter {

    fun toMovieDetails(id: Long, externalMovieDetails: ExternalMovieDetails) = MovieDetails(
        id = id,
        title = externalMovieDetails.title,
        year = externalMovieDetails.year,
        rated = externalMovieDetails.rated,
        released = externalMovieDetails.released,
        runtime = externalMovieDetails.runtime,
        genres = externalMovieDetails.genre.split(", "),
        director = externalMovieDetails.director,
        writers = externalMovieDetails.writer.split(", "),
        actors = externalMovieDetails.actors.split(", "),
        plot = externalMovieDetails.plot,
        language = externalMovieDetails.language.split(", "),
        countrys = externalMovieDetails.country.split(", "),
        awards = externalMovieDetails.awards,
        poster = externalMovieDetails.poster,
        ratings = externalMovieDetails.ratings.map { toMovieRating(it) }.toSet(),
        metascore = externalMovieDetails.metascore.toInt(),
        imdbRating = externalMovieDetails.imdbRating.toDouble(),
        imdbVotes = externalMovieDetails.imdbVotes.replace(",", "").toInt(),
        imdbId = externalMovieDetails.imdbId,
        type = externalMovieDetails.type,
        dvd = externalMovieDetails.dvd,
        boxOffice = externalMovieDetails.boxOffice,
        production = externalMovieDetails.production,
        website = externalMovieDetails.website,
        response = externalMovieDetails.response
    )

    private fun toMovieRating(externalMovieRating: ExternalMovieRating) = MovieRating(
            source = externalMovieRating.source,
            value = externalMovieRating.value
    )
}
