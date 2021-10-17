package com.github.brunotorrao.furious.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ExternalMovieDetails(
    @field:JsonProperty("Title")
    val title: String,
    @field:JsonProperty("Year")
    val year: String,
    @field:JsonProperty("Rated")
    val rated: String,
    @field:JsonProperty("Released")
    val released: String,
    @field:JsonProperty("Runtime")
    val runtime: String,
    @field:JsonProperty("Genre")
    val genre: String,
    @field:JsonProperty("Director")
    val director: String,
    @field:JsonProperty("Writer")
    val writer: String,
    @field:JsonProperty("Actors")
    val actors: String,
    @field:JsonProperty("Plot")
    val plot: String,
    @field:JsonProperty("Language")
    val language: String,
    @field:JsonProperty("Country")
    val country: String,
    @field:JsonProperty("Awards")
    val awards: String? = null,
    @field:JsonProperty("Poster")
    val poster: String,
    @field:JsonProperty("Ratings")
    val ratings: Set<ExternalMovieRating> = setOf(),
    @field:JsonProperty("Metascore")
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    @field:JsonProperty("imdbID")
    val imdbId: String,
    @field:JsonProperty("Type")
    val type: String,
    @field:JsonProperty("DVD")
    val dvd: String,
    @field:JsonProperty("BoxOffice")
    val boxOffice: String,
    @field:JsonProperty("Production")
    val production: String,
    @field:JsonProperty("Website")
    val website: String,
    @field:JsonProperty("Response")
    val response: String

)

data class ExternalMovieRating(
    @field:JsonProperty("Source")
    val source: String,
    @field:JsonProperty("Value")
    val value: String
)
