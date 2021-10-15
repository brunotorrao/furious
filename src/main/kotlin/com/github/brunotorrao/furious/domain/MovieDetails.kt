package com.github.brunotorrao.furious.domain

data class MovieDetails(
    val id: Long,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genres: List<String>,
    val director: String,
    val writers: List<String>,
    val actors: List<String>,
    val plot: String,
    val language: List<String>,
    val countrys: List<String>,
    val awards: String? = null,
    val poster: String,
    val ratings: Set<MovieRating> = setOf(),
    val metascore: Int,
    val imdbRating: Double,
    val imdbVotes: Int,
    val imdbId: String,
    val type: String,
    val dvd: String,
    val boxOffice: String,
    val production: String,
    val website: String,
    val response: String
)

data class MovieRating(
    val source: String,
    val value: String
)
