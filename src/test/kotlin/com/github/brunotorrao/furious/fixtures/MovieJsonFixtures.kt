package com.github.brunotorrao.furious.fixtures

fun jsonForAllMovies() = """
                [
                  {
                    "id": 1,
                    "title": "The Fast and the Furious",
                    "externalId": "tt0232500"
                  },
                  {
                    "id": 2,
                    "title": "2 Fast 2 Furious",
                    "externalId": "tt0322259"
                  },
                  {
                    "id": 3,
                    "title": "The Fast and the Furious: Tokyo Drift",
                    "externalId": "tt0463985"
                  },
                  {
                    "id": 4,
                    "title": "Fast & Furious",
                    "externalId": "tt1013752"
                  },
                  {
                    "id": 5,
                    "title": "Fast Five",
                    "externalId": "tt1596343"
                  },
                  {
                    "id": 6,
                    "title": "Fast & Furious 6",
                    "externalId": "tt1905041"
                  },
                  {
                    "id": 7,
                    "title": "Furious 7",
                    "externalId": "tt2820852"
                  },
                  {
                    "id": 8,
                    "title": "The Fate of the Furious",
                    "externalId": "tt4630562"
                  }
                ]
            """.trimIndent()

fun jsonForMovieById(id: Long = 1) = """
        {
          "id": ${id},
          "title": "The Fast and the Furious",
          "year": "2001",
          "rated": "PG-13",
          "released": "22 Jun 2001",
          "runtime": "106 min",
          "genres": [
            "Action",
            "Crime",
            "Thriller"
          ],
          "director": "Rob Cohen",
          "writers": [
            "Ken Li",
            "Gary Scott Thompson",
            "Erik Bergquist"
          ],
          "actors": [
            "Vin Diesel",
            "Paul Walker",
            "Michelle Rodriguez"
          ],
          "plot": "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.",
          "language": [
            "English",
            "Spanish"
          ],
          "countrys": [
            "United States",
            "Germany"
          ],
          "awards": "11 wins & 18 nominations",
          "poster": "https://m.media-amazon.com/images/M/MV5BNzlkNzVjMDMtOTdhZC00MGE1LTkxODctMzFmMjkwZmMxZjFhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
          "ratings": [
            {
              "source": "Internet Movie Database",
              "value": "6.8/10"
            },
            {
              "source": "Rotten Tomatoes",
              "value": "54%"
            },
            {
              "source": "Metacritic",
              "value": "58/100"
            }
          ],
          "metascore": 58,
          "imdbRating": 6.8,
          "imdbVotes": 367679,
          "imdbId": "tt0232500",
          "type": "movie",
          "dvd": "03 Jun 2003",
          "boxOffice": "${'$'}144,533,925",
          "production": "N/A",
          "website": "N/A",
          "response": "True"
        }
        """.trimIndent()
