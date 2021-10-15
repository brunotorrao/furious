package com.github.brunotorrao.furious

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

fun stubForImdbGet(id: String) {
    WireMock.stubFor(
        WireMock.get("/?apikey=1234&i=${id}")
            .willReturn(
                WireMock.ok(
                    """
                {
                  "Title": "The Fast and the Furious",
                  "Year": "2001",
                  "Rated": "PG-13",
                  "Released": "22 Jun 2001",
                  "Runtime": "106 min",
                  "Genre": "Action, Crime, Thriller",
                  "Director": "Rob Cohen",
                  "Writer": "Ken Li, Gary Scott Thompson, Erik Bergquist",
                  "Actors": "Vin Diesel, Paul Walker, Michelle Rodriguez",
                  "Plot": "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.",
                  "Language": "English, Spanish",
                  "Country": "United States, Germany",
                  "Awards": "11 wins & 18 nominations",
                  "Poster": "https://m.media-amazon.com/images/M/MV5BNzlkNzVjMDMtOTdhZC00MGE1LTkxODctMzFmMjkwZmMxZjFhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
                  "Ratings": [
                    {
                      "Source": "Internet Movie Database",
                      "Value": "6.8/10"
                    },
                    {
                      "Source": "Rotten Tomatoes",
                      "Value": "54%"
                    },
                    {
                      "Source": "Metacritic",
                      "Value": "58/100"
                    }
                  ],
                  "Metascore": "58",
                  "imdbRating": "6.8",
                  "imdbVotes": "367,679",
                  "imdbID": "tt0232500",
                  "Type": "movie",
                  "DVD": "03 Jun 2003",
                  "BoxOffice": "${'$'}144,533,925",
                  "Production": "N/A",
                  "Website": "N/A",
                  "Response": "True"
                }
            """.trimIndent()
                ).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            )
    )
}

fun stubForImdbGetIncorrectImdbId(id: String) {
    WireMock.stubFor(
        WireMock.get("/?apikey=1234&i=${id}")
            .willReturn(
                WireMock.ok(
                    """
                {
                  "Response": "False",
                  "Error": "Incorrect IMDb ID."
                }
            """.trimIndent()
                ).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            )
    )
}
