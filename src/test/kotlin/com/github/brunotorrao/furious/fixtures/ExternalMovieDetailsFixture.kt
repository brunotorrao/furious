package com.github.brunotorrao.furious.fixtures

import com.github.brunotorrao.furious.domain.ExternalMovieDetails
import com.github.brunotorrao.furious.domain.ExternalMovieRating

fun simpleExternalMovieDetails() = ExternalMovieDetails(
    title = "The Fast and the Furious",
    year = "2001",
    rated = "PG-13",
    released = "22 Jun 2001",
    runtime = "106 min",
    genre = "Action, Crime, Thriller",
    director = "Rob Cohen",
    writer = "Ken Li, Gary Scott Thompson, Erik Bergquist",
    actors = "Vin Diesel, Paul Walker, Michelle Rodriguez",
    plot = "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.",
    language = "English, Spanish",
    country = "United States, Germany",
    awards = "11 wins & 18 nominations",
    poster = "https://m.media-amazon.com/images/M/MV5BNzlkNzVjMDMtOTdhZC00MGE1LTkxODctMzFmMjkwZmMxZjFhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    ratings = setOf(
        ExternalMovieRating(
            source = "Internet Movie Database",
            value = "6.8/10"
        ),
        ExternalMovieRating(
            source = "Rotten Tomatoes",
            value = "54%"
        ),
        ExternalMovieRating(
            source = "Metacritic",
            value = "58/100"
        )
    ),
    metascore = "58",
    imdbRating = "6.8",
    imdbVotes = "367,679",
    imdbId = "tt0232500",
    type = "movie",
    dvd = "03 Jun 2003",
    boxOffice = "$144,533,925",
    production = "N/A",
    website = "N/A",
    response = "True"
)
