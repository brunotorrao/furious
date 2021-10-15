package com.github.brunotorrao.furious.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ExternalMovieDetails(
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Year")
    val year: String,
    @JsonProperty("Rated")
    val rated: String,
    @JsonProperty("Released")
    val released: String,
    @JsonProperty("Runtime")
    val runtime: String,
    @JsonProperty("Genre")
    val genre: String,
    @JsonProperty("Director")
    val director: String,
    @JsonProperty("Writer")
    val writer: String,
    @JsonProperty("Actors")
    val actors: String,
    @JsonProperty("Plot")
    val plot: String,
    @JsonProperty("Language")
    val language: String,
    @JsonProperty("Country")
    val country: String,
    @JsonProperty("Awards")
    val awards: String? = null,
    @JsonProperty("Poster")
    val poster: String,
    @JsonProperty("Ratings")
    val ratings: Set<ExternalMovieRating> = setOf(),
    @JsonProperty("Metascore")
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    @JsonProperty("imdbID")
    val imdbId: String,
    @JsonProperty("Type")
    val type: String,
    @JsonProperty("DVD")
    val dvd: String,
    @JsonProperty("BoxOffice")
    val boxOffice: String,
    @JsonProperty("Production")
    val production: String,
    @JsonProperty("Website")
    val website: String,
    @JsonProperty("Response")
    val response: String

)

data class ExternalMovieRating(
    @JsonProperty("Source")
    val source: String,
    @JsonProperty("Value")
    val value: String
)
